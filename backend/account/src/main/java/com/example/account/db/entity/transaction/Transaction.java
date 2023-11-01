package com.example.account.db.entity.transaction;

import com.example.account.db.entity.account.Account;
import com.example.account.db.entity.transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Transaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name="company_name",length = 30,nullable = false)
    private String recipient; // 거래대상
    @Column(name="business_category",nullable = true)
    private Integer businessCategory; // 거래한 회사의 카테고리
    @Enumerated(EnumType.STRING)
    @Column(name="transaction_type",length = 10,nullable = false)
    private TransactionType transactionType; // 거래 타입
    @Column(name="paymentAmount", nullable = false)
    private Long paymentAmount; // 거래 금액
    @Column(name="balance", nullable = false)
    private Long balance; // 거래 후 잔액
    @Column(name="transaction_time", updatable = false,nullable = false)
    private LocalDateTime transactionTime;

    public Transaction(Account account, String recipient, Integer businessCategory, TransactionType transactionType, Long paymentAmount, Long balance) {
        this.account = account;
        this.recipient = recipient;
        this.businessCategory = businessCategory;
        this.transactionType = transactionType;
        this.paymentAmount = paymentAmount;
        this.balance = balance;
    }

    public Transaction(Account account, String recipient, TransactionType transactionType, Long paymentAmount, Long balance) {
        this.account = account;
        this.recipient = recipient;
        this.transactionType = transactionType;
        this.paymentAmount = paymentAmount;
        this.balance = balance;
    }

}
