package com.example.account.service.impl;

import com.example.account.api.request.transaction.TransactionPeriodRequest;
import com.example.account.api.request.transaction.TransactionRequest;
import com.example.account.api.response.transaction.TransactionAccountResponse;
import com.example.account.api.response.transaction.TransactionResponse;
import com.example.account.common.api.exception.InsufficientBalanceException;
import com.example.account.common.api.exception.NotFoundException;
import com.example.account.db.entity.account.Account;
import com.example.account.db.entity.account.AccountState;
import com.example.account.db.entity.transaction.Transaction;
import com.example.account.db.repository.AccountRepository;
import com.example.account.db.repository.TransactionRepository;
import com.example.account.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.account.common.api.status.FailCode.*;
import static com.example.account.common.api.status.FailCode.NOT_USABLE_COMPANY_ACCOUNT;

@Service
@Transactional
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    @Override
    public TransactionAccountResponse getHomeAccountDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        return new TransactionAccountResponse(account);
    }

    // 반려동물 용품 구입 관련 거래내역 추가
    @Override
    public Long addTransaction(TransactionRequest transactionRequest) {
        Long myAccountId = transactionRequest.getMyAccountId();
        Long companyAccountId = transactionRequest.getCompanyAccountId();

        Account myAccount = accountRepository.findById(myAccountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        Account companyAccount = accountRepository.findById(companyAccountId).orElseThrow(() -> new NotFoundException(NO_COMPANY_ACCOUNT));

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
    public List<TransactionResponse> getTransactionHistory(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        List<Transaction> transactions = transactionRepository.findByAccount(account);
        List<TransactionResponse> result = transactions.stream().map(transaction -> new TransactionResponse(transaction)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<TransactionResponse> getSpecificPeriodTransaction(TransactionPeriodRequest request) {
        Account account = accountRepository.findById(request.getAccountId()).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        LocalDateTime startDate = request.getStart().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.of(request.getEnd(), LocalTime.of(23, 59, 59));

        List<Transaction> transactions=transactionRepository
                .findByTransactionTimeBetweenOrderByTransactionTimeDesc(startDate,endDate);
        return transactions.stream().map(TransactionResponse::new).collect(Collectors.toList());
    }
}
