package com.ssafy.account.service.impl;

import com.ssafy.account.api.request.transaction.RemittanceRequest;
import com.ssafy.account.api.request.transaction.TransactionPeriodRequest;
import com.ssafy.account.api.request.transaction.TransactionRequest;
import com.ssafy.account.api.response.transaction.TransactionAccountResponse;
import com.ssafy.account.api.response.transaction.TransactionResponse;
import com.ssafy.account.common.api.exception.InsufficientBalanceException;
import com.ssafy.account.common.api.exception.NotCorrectException;
import com.ssafy.account.common.api.exception.NotFoundException;
import com.ssafy.account.db.entity.access.Access;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.account.AccountState;
import com.ssafy.account.db.entity.transaction.Transaction;
import com.ssafy.account.db.repository.AccessRepository;
import com.ssafy.account.db.repository.AccountRepository;
import com.ssafy.account.db.repository.TransactionRepository;
import com.ssafy.account.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.account.common.api.status.FailCode.*;
import static com.ssafy.account.common.api.status.FailCode.NOT_USABLE_COMPANY_ACCOUNT;

@Service
@Transactional
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccessRepository accessRepository;
    @Override
    public TransactionAccountResponse getHomeAccountDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        return new TransactionAccountResponse(account);
    }

    // 반려동물 용품 구입 관련 거래내역 추가
    @Override
    public Long addPetRelatedTransaction(TransactionRequest transactionRequest) {

        Long myAccountId = transactionRequest.getMyAccountId();
        Long companyAccountId = transactionRequest.getCompanyAccountId();

        Account myAccount = accountRepository.findById(myAccountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        Account companyAccount = accountRepository.findById(companyAccountId).orElseThrow(() -> new NotFoundException(NO_COMPANY_ACCOUNT));

        // 입력된 RFID코드가 맞는지 확인
        String rfidCode = transactionRequest.getRfidCode();
        if(!myAccount.getRfidCode().equals(AccountServiceImpl.hashPassword(rfidCode))) {
            throw new NotCorrectException(DIFFERENT_RFID);
        }

        // 사용 가능 계좌인지 확인
        if(myAccount.getState() == AccountState.CLOSED || myAccount.getState() == AccountState.LOCKED || myAccount.getState() == AccountState.SUSPENDED) {
            throw new NotFoundException(NOT_USABLE_ACCOUNT);
        }
        if(companyAccount.getState() == AccountState.CLOSED || companyAccount.getState() == AccountState.LOCKED || myAccount.getState() == AccountState.SUSPENDED) {
            throw new NotFoundException(NOT_USABLE_COMPANY_ACCOUNT);
        }

        Long pay = transactionRequest.getPaymentAmount();
        // 잔액이 결제금액보다 부족하면 예외 발생
        if(myAccount.getBalance() - pay < 0) throw new InsufficientBalanceException(REJECT_ACCOUNT_PAYMENT);
        
        myAccount.minusBalance(pay);
        companyAccount.addBalance(pay);

        Transaction transaction = new Transaction(myAccount, companyAccount.getDepositorName(), companyAccount.getBusinessType(), transactionRequest.getTransactionType(), pay, myAccount.getBalance());
        transactionRepository.save(transaction);
        return transaction.getId();
    }

    @Override
    public Long addRemittanceTransaction(RemittanceRequest remittanceRequest) {
        
        // 입력된 비밀번호가 맞는지 확인
        
        Long myAccountId = remittanceRequest.getMyAccountId();
        Long receiverAccountId = remittanceRequest.getReceiverAccountId();

        Account myAccount = accountRepository.findById(myAccountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        Account receiverAccount = accountRepository.findById(receiverAccountId).orElseThrow(() -> new NotFoundException(NO_RECEIVER_ACCOUNT));

        // 입력된 비밀번호가 맞는지 확인
        String password = remittanceRequest.getPassword();
        if (!myAccount.getAccountPassword().equals(AccountServiceImpl.hashPassword(password))) {
            throw new NotCorrectException(DIFFERENT_PASSWORD);
        }

        // 사용 가능 계좌인지 확인
        if(myAccount.getState() == AccountState.CLOSED || myAccount.getState() == AccountState.LOCKED || myAccount.getState() == AccountState.SUSPENDED) {
            throw new NotFoundException(NOT_USABLE_ACCOUNT);
        }
        if(receiverAccount.getState() == AccountState.CLOSED || receiverAccount.getState() == AccountState.LOCKED || myAccount.getState() == AccountState.SUSPENDED) {
            throw new NotFoundException(NOT_USABLE_RECEIVER_ACCOUNT);
        }

        Long remittanceAmount = remittanceRequest.getRemittanceAmount();
        // 잔액이 결제금액보다 부족하면 예외 발생
        if(myAccount.getBalance() - remittanceAmount < 0) throw new InsufficientBalanceException(REJECT_ACCOUNT_REMITTANCE);

        myAccount.minusBalance(remittanceAmount);
        receiverAccount.addBalance(remittanceAmount);

        Transaction transaction = new Transaction(myAccount, receiverAccount.getDepositorName(), remittanceRequest.getTransactionType(), remittanceAmount, myAccount.getBalance());
        transactionRepository.save(transaction);
        return transaction.getId();
    }

    @Override
    public List<TransactionResponse> getTransactionHistory(Long memberId, Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        
        // 접근하려는 사람의 id(requestMemberId)와 계좌번호(accountNumber)를 활용하여
        // 접근 권한이 있는 유저인지 확인
        // 본인이면 당연히 접근가능
        Access access = accessRepository.findAccessByRequestMemberIdAndAccountNumber(memberId, account.getAccountNumber());
        if(!account.getMemberId().equals(memberId) && access == null) {
            throw new NotFoundException(NO_PERMISSION_TO_TRANSACTION);
        }
        
        List<Transaction> transactions = transactionRepository.findByAccount(account);
        List<TransactionResponse> result = transactions.stream().map(transaction -> new TransactionResponse(transaction)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<TransactionResponse> getSpecificPeriodTransaction(Long memberId, TransactionPeriodRequest request) {
        Account account = accountRepository.findById(request.getAccountId()).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));

        // 접근하려는 사람의 id(requestMemberId)와 계좌번호(accountNumber)를 활용하여
        // 접근 권한이 있는 유저인지 확인
        // 본인이면 당연히 접근가능
        Access access = accessRepository.findAccessByRequestMemberIdAndAccountNumber(memberId, account.getAccountNumber());
        if(!account.getMemberId().equals(memberId) && access == null) {
            throw new NotFoundException(NO_PERMISSION_TO_TRANSACTION);
        }

        LocalDateTime startDate = request.getStart().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.of(request.getEnd(), LocalTime.of(23, 59, 59));

        List<Transaction> transactions=transactionRepository
                .findByTransactionTimeBetweenOrderByTransactionTimeDesc(startDate,endDate);
        return transactions.stream().map(TransactionResponse::new).collect(Collectors.toList());
    }
}
