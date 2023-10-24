package com.example.account.api.response;

import com.example.account.db.entity.Account;
import com.example.account.db.entity.Transaction;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {

    private Long id;
    private String companyName;

    private String transactionType; // 거래 타입
    private Long pay; // 거래 금액
    private Long balance; // 거래 후 잔액
    private LocalDateTime transactionDate; // 거래일자

    private String desc; // 거래 메모

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.companyName = transaction.getCompanyName();
        this.transactionType = transaction.getTransactionType();
        this.pay = transaction.getPay();
        this.balance = transaction.getBalance();
        this.transactionDate = transaction.getTransactionDate();
    }
}
