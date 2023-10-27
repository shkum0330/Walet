package com.example.account.api.response;

import com.example.account.db.entity.Account;
import com.example.account.db.entity.AccountState;
import com.example.account.db.entity.Transaction;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GeneralAccountDetailResponse {

    private Long id;
    private String accountNumber; // 계좌번호
    private Long balance; // 잔액
    private AccountState state; // 상태
    private LocalDateTime createdAt; // 등록일
    private Long accountLimit; // 인출한도
    private boolean accountType; // 타입

    private Long linkedAccountId; // 연결된 모계좌 id

    // 최신 거래 내역 5개
    private List<Transaction> recentTransactions; // 거래내역

    public GeneralAccountDetailResponse(Account account, List<Transaction> recentTransactions) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
        this.state = account.getState();
        this.createdAt = account.getCreatedAt();
        this.accountLimit = account.getAccountLimit();
        this.accountType = account.getAccountType();
        this.linkedAccountId = account.getLinkedAccountId();
        this.recentTransactions = new ArrayList<>(recentTransactions);
    }
}
