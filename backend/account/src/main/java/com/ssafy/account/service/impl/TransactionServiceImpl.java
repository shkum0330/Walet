package com.ssafy.account.service.impl;

import com.ssafy.account.api.request.transaction.RemittanceRequest;
import com.ssafy.account.api.request.transaction.TransactionPeriodRequest;
import com.ssafy.account.api.request.transaction.TransactionRequest;
import com.ssafy.account.api.response.transaction.*;
import com.ssafy.account.common.api.exception.InsufficientBalanceException;
import com.ssafy.account.common.api.exception.NotCorrectException;
import com.ssafy.account.common.api.exception.NotFoundException;
import com.ssafy.account.common.api.exception.RestrictedBusinessException;
import com.ssafy.account.common.domain.util.EncryptUtil;
import com.ssafy.account.db.entity.access.Access;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.transaction.Transaction;
import com.ssafy.account.db.entity.transaction.TransactionType;
import com.ssafy.account.db.repository.AccessRepository;
import com.ssafy.account.db.repository.AccountRepository;
import com.ssafy.account.db.repository.TransactionRepository;
import com.ssafy.account.service.MessageSenderService;
import com.ssafy.account.service.TransactionService;
import com.ssafy.external.service.NHFintechService;
import com.ssafy.external.service.OauthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.account.common.api.status.FailCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccessRepository accessRepository;
    private final MessageSenderService messageSenderService;
    private final NHFintechService nhFintechService;
    private final OauthService oauthService;
    private final EncryptUtil encryptUtil;

    @Override
    public TransactionAccountResponse getTransactionAccountDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        return new TransactionAccountResponse(account);
    }

    @Override
    public PetInfoResponse getPetInfoByRfid(String rfidCode) {
        Account petAccount = accountRepository.findByRfidCodeAndAccountState(rfidCode, "00").orElseThrow(() -> new NotFoundException(NO_PET_ACCOUNT_WITH_AUTH_INFO));
        return new PetInfoResponse(petAccount);
    }

    @Override
    public ReceiverInfoResponse getReceiverInfoByAccountNumber(String accountNumber, Long paymentAmount) {
        Account receiverAccount = accountRepository.findByAccountNumberAndAccountState(accountNumber, "00").orElseThrow(() -> new NotFoundException(NOT_USABLE_RECEIVER_ACCOUNT));
        return new ReceiverInfoResponse(receiverAccount, paymentAmount);
    }

    // 반려동물 용품 구입 관련 결제 및 거래내역 추가
    @Override
    @Transactional
    public Long addPetRelatedTransaction(TransactionRequest transactionRequest) {

        Long myAccountId = transactionRequest.getMyAccountId();
        Long companyAccountId = transactionRequest.getCompanyAccountId();

        Account myPetAccount = accountRepository.findByMemberIdAndAccountTypeAndAccountState(myAccountId,"02", "00").orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        Account companyAccount = accountRepository.findByMemberIdAndAccountTypeAndAccountState(companyAccountId,"01", "00").orElseThrow(() -> new NotFoundException(NO_COMPANY_ACCOUNT));

        int limitTypes= myPetAccount.getLimitTypes();
        int businessType=(int)Math.pow(2,companyAccount.getBusinessType()-1);
        log.info("{} {}",limitTypes,businessType);

        // 비트 연산으로 제한업종에 걸리는지 확인
        if((limitTypes & businessType) != 0){
            throw new RestrictedBusinessException(RESTRICTED_BUSINESS);
        }

        Long pay = transactionRequest.getPaymentAmount();
        // 잔액이 결제금액보다 부족하면 예외 발생
        if(myPetAccount.getBalance() - pay < 0) throw new InsufficientBalanceException(REJECT_ACCOUNT_PAYMENT);

        myPetAccount.minusBalance(pay);
        companyAccount.addBalance(pay);

        Transaction myTransaction = new Transaction(myPetAccount, companyAccount.getDepositorName(), companyAccount.getBusinessType(), TransactionType.WITHDRAWAL, pay, myPetAccount.getBalance());
        Transaction companyTransaction = new Transaction(companyAccount, myPetAccount.getDepositorName(), companyAccount.getBusinessType(), TransactionType.DEPOSIT, pay, companyAccount.getBalance());
        log.info("{}",myTransaction);
        log.info("{}",companyTransaction);
        transactionRepository.save(myTransaction);
        transactionRepository.save(companyTransaction);

        // 거래가 완료되면 메시지를 알림 서버에 보내자.
//        messageSenderService.sendPaymentMessage(String.valueOf(myAccountId)
//                ,String.valueOf(transactionRequest.getPaymentAmount()),companyAccount.getDepositorName());
        return myTransaction.getId();
    }

    // 송금 후 거래내역 기록까지
    @Override
    @Transactional
    public Long addRemittanceTransaction(RemittanceRequest remittanceRequest) {

        Long myAccountId = remittanceRequest.getMyAccountId();
        Long receiverAccountId = remittanceRequest.getReceiverAccountId();

        Account myAccount = accountRepository.findByIdAndAccountState(myAccountId, "00").orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        Account receiverAccount = accountRepository.findByIdAndAccountState(receiverAccountId, "00").orElseThrow(() -> new NotFoundException(NO_RECEIVER_ACCOUNT));

        // 입력된 비밀번호가 맞는지 확인
        String password = remittanceRequest.getPassword();
        if (!myAccount.getAccountPassword().equals(encryptUtil.hashPassword(password))) {
            throw new NotCorrectException(DIFFERENT_PASSWORD);
        }

        // 사용 가능 계좌인지 확인
//        if(myAccount.getAccountState().equals("01") || myAccount.getAccountState().equals("10") || myAccount.getAccountState().equals("11")) {
//            throw new NotFoundException(NOT_USABLE_ACCOUNT);
//        }
//        if(receiverAccount.getAccountState().equals("01") || receiverAccount.getAccountState().equals("10") || receiverAccount.getAccountState().equals("11")) {
//            throw new NotFoundException(NOT_USABLE_RECEIVER_ACCOUNT);
//        }

        Long remittanceAmount = remittanceRequest.getRemittanceAmount();
        // 잔액이 송금금액보다 부족하면 예외 발생
        if(myAccount.getBalance() - remittanceAmount < 0) throw new InsufficientBalanceException(REJECT_ACCOUNT_REMITTANCE);

        // 농협api로 송금 진행
        nhFintechService.remittance(myAccount,receiverAccount,remittanceAmount);

        myAccount.minusBalance(remittanceAmount);
        receiverAccount.addBalance(remittanceAmount);

        Transaction myTransaction = new Transaction(myAccount, receiverAccount.getDepositorName(), TransactionType.WITHDRAWAL, remittanceAmount, myAccount.getBalance());
        Transaction receiverTransaction = new Transaction(receiverAccount, myAccount.getDepositorName(), TransactionType.DEPOSIT, remittanceAmount, receiverAccount.getBalance());

        transactionRepository.save(myTransaction);
        transactionRepository.save(receiverTransaction);
        return myTransaction.getId();
    }

    @Override
    @Cacheable(value = "txHistory", key = "#memberId")
    public List<TransactionResponse> getTransactionHistory(Long memberId, Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        
        // 접근하려는 사람의 id(requestMemberId)와 계좌번호(accountNumber)를 활용하여
        // 접근 권한이 있는 유저인지 확인
        // 본인이면 당연히 접근가능
        Access access = accessRepository.findAccessByRequestMemberIdAndAccountNumberAndIsConfirmed(memberId, account.getAccountNumber(), 1);
        if(!account.getMemberId().equals(memberId) && access == null) {
            throw new NotFoundException(NO_PERMISSION_TO_TRANSACTION);
        }
        
        List<Transaction> transactions = transactionRepository.findByAccount(account);
        List<TransactionResponse> result = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionType transactionType = transaction.getTransactionType();

            if(transactionType == TransactionType.DEPOSIT || transactionType == TransactionType.TRANSFER) {
                result.add(new TransactionResponse(transaction.getAccount().getDepositorName(), transaction));
            }
            else if (transactionType == TransactionType.WITHDRAWAL) {
                result.add(new TransactionResponse(transaction.getRecipient(), transaction));
            }

        }
        return result;
    }

    @Override
    public List<TransactionResponse> getSpecificPeriodTransaction(Long memberId, TransactionPeriodRequest request) {
        Account account = accountRepository.findById(request.getAccountId()).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));

        // 접근하려는 사람의 id(requestMemberId)와 계좌번호(accountNumber)를 활용하여
        // 접근 권한이 있는 유저인지 확인
        // 본인이면 당연히 접근가능
        Access access = accessRepository.findAccessByRequestMemberIdAndAccountNumberAndIsConfirmed(memberId, account.getAccountNumber(), 1);
        if(!account.getMemberId().equals(memberId) && access == null) {
            throw new NotFoundException(NO_PERMISSION_TO_TRANSACTION);
        }

        LocalDateTime startDate = request.getStart().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.of(request.getEnd(), LocalTime.of(23, 59, 59));

        List<Transaction> transactions=transactionRepository
                .findByTransactionTimeBetweenOrderByTransactionTimeDesc(startDate,endDate);

        List<TransactionResponse> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionType transactionType = transaction.getTransactionType();
            if (transactionType == TransactionType.DEPOSIT || transactionType == TransactionType.TRANSFER) {
                result.add(new TransactionResponse(transaction.getAccount().getDepositorName(), transaction));
            }
            else if (transactionType == TransactionType.WITHDRAWAL) {
                result.add(new TransactionResponse(transaction.getRecipient(), transaction));
            }
        }
        return result;
    }

    @Override
    public TransactionDetailResponse getTransactionDetail(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new NotFoundException(NO_TRANSACTION_DATA));

        Long memberId = transaction.getAccount().getMemberId();
        // memberId를 써서 해당 회원의 전화번호를 갖고오자

        int category = transaction.getBusinessCategory();
        String businessCategory = null;

//        반려동물 관련 업종 카테고리
//        1. 동물병원
//        2. 반려동물용품
//        3. 반려동물미용
//        4. 애견카페
//        5. 반려견놀이터

        // businessCategory를 String으로 변환
        switch(category) {
            case 1:
                businessCategory = "동물병원";
                break;
            case 2:
                businessCategory = "반려동물용품";
                break;
            case 3:
                businessCategory = "반려동물미용";
                break;
            case 4:
                businessCategory = "애견카페";
                break;
            case 5:
                businessCategory = "반려견놀이터";
                break;
        }

        String userPhoneNumber = oauthService.getUserPhoneNumber(memberId);
        return new TransactionDetailResponse(transaction, businessCategory, userPhoneNumber);
    }

    @Override
    public List<AdminMemberAccountTransactionResponse> getTransactionsForAdmin(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        List<AdminMemberAccountTransactionResponse> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionType transactionType = transaction.getTransactionType();
            if (transactionType == TransactionType.DEPOSIT || transactionType == TransactionType.TRANSFER) {
                result.add(new AdminMemberAccountTransactionResponse(transaction, transaction.getAccount().getDepositorName(), TransactionType.DEPOSIT.getName()));
            }
            else if(transactionType == TransactionType.WITHDRAWAL) {
                result.add(new AdminMemberAccountTransactionResponse(transaction, transaction.getRecipient(), TransactionType.WITHDRAWAL.getName()));
            }
        }

        return result;
    }
}
