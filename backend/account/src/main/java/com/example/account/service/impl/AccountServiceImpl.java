package com.example.account.service.impl;

import com.example.account.api.request.AccountRequest;
import com.example.account.api.request.AnimalAccountRequest;
import com.example.account.api.request.TransactionRequest;
import com.example.account.api.response.AnimalAccountDetailResponse;
import com.example.account.api.response.GeneralAccountDetailResponse;
import com.example.account.api.response.TransactionResponse;
import com.example.account.common.api.exception.NotFoundException;
import com.example.account.db.entity.Account;
import com.example.account.db.entity.AccountState;
import com.example.account.db.entity.Transaction;
import com.example.account.db.repository.AccountRepository;
import com.example.account.db.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.example.account.common.api.status.FailCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    // 일반계좌 상세정보
    public GeneralAccountDetailResponse getGeneralAccountDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));

        // 최근 5개의 거래내역을 가져옴
        List<Transaction> transactionHistory = account.getTransactionHistory();
        Collections.sort(transactionHistory, (transaction1, transaction2)
                -> transaction2.getCreatedAt().compareTo(transaction1.getCreatedAt()));
        List<Transaction> recentTransactions = transactionHistory.subList(0, Math.min(5, transactionHistory.size()));

        GeneralAccountDetailResponse result = new GeneralAccountDetailResponse(account, recentTransactions);
        return result;
    }

    // 반려동물계좌 상세정보
    public AnimalAccountDetailResponse getAnimalAccountDetail(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));

        // 최근 5개의 거래내역을 가져옴
        List<Transaction> transactionHistory = account.getTransactionHistory();
        Collections.sort(transactionHistory, (transaction1, transaction2)
                -> transaction2.getCreatedAt().compareTo(transaction1.getCreatedAt()));
        List<Transaction> recentTransactions = transactionHistory.subList(0, Math.min(5, transactionHistory.size()));

        AnimalAccountDetailResponse result = new AnimalAccountDetailResponse(account, recentTransactions);
        return result;
    }

    // 반려동물 용품 구입 관련 거래내역 추가
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

        Long pay = transactionRequest.getPay();
        myAccount.minusBalance(pay);
        companyAccount.addBalance(pay);

        Transaction transaction = new Transaction(myAccount, companyAccount.getDepositorName(), transactionRequest.getTransactionType(), pay, myAccount.getBalance());
        myAccount.addTransaction(transaction);
        transactionRepository.save(transaction);
        return transaction.getId();
    }

    // 거래내역 조회
    public List<TransactionResponse> getTransactionHistory(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(NO_ACCOUNT));
        List<Transaction> transactions = account.getTransactionHistory();
        List<TransactionResponse> result = transactions.stream().map(transaction -> new TransactionResponse(transaction)).collect(Collectors.toList());
        return result;
    }

    // 일반계좌 발급
    public Long registGeneralAccount(AccountRequest accountRequest) {
        Account account = new Account(accountRequest);

        // 우선 랜덤으로 13자리의 계좌번호 부여
        int length = 13;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        String accountNumber = sb.toString();
        account.createAccountNumber(accountNumber);
        
        // 계좌 정보를 DB에 저장
        accountRepository.save(account);
        return account.getId();
    }

    // 동물계좌 발급
    // 동물계좌 생성 버튼 클릭 -> 모계좌를 연결할 사람은 기존에 있던 농협 계좌 중 선택(필수사항x) -> 내 반려동물 정보 입력 -> 동물계좌 완성
    public Long registAnimalAccount(AnimalAccountRequest animalAccountRequest) {
        
        Account animalAccount = new Account(animalAccountRequest);

        // 우선 랜덤으로 13자리의 계좌번호 부여
        int length = 13;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        String accountNumber = sb.toString();
        animalAccount.createAccountNumber(accountNumber);

        // 계좌 정보를 DB에 저장
        accountRepository.save(animalAccount);
        return animalAccount.getId();
    }

    // 동물계좌 양도
    // 상대방 반려동물 계좌 생성 -> 이전 주인의 반려동물 계좌에서 돈 전부 이체 -> 이전 주인의 반려동물 계좌 삭제

    // 계좌 상태 변경

    // 추가적으로 제공되는 농협 api
    // 1. 예금주 조회
    // 2. 예금주 실명확인
}
