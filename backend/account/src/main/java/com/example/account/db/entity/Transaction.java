package com.example.account.db.entity;

import com.example.account.api.request.TransactionRequest;
import com.example.account.common.domain.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Transaction extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account myAccount;
    private String companyName; // 거래대상회사

    private String transactionType; // 거래 타입
    private Long pay; // 거래 금액
    private Long balance; // 거래 후 잔액

    public Transaction(Account myAccount, String companyName, String transactionType, Long pay, Long balance) {
        this.myAccount = myAccount;
        this.companyName = companyName;
        this.transactionType = transactionType;
        this.pay = pay;
        this.balance = balance;
    }

}
