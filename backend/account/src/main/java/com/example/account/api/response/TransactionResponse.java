package com.example.account.api.response;

import com.example.account.db.entity.Transaction;
import com.example.account.db.entity.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponse {

    private Long id;
    private String companyName;

    private TransactionType transactionType; // 거래 타입
    private Long paymentAmount; // 거래 금액
    private Long balance; // 거래 후 잔액
    private LocalDateTime transactionDate; // 거래일자

    private String desc; // 거래 메모

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.companyName = transaction.getCompanyName();
        this.transactionType = transaction.getTransactionType();
        this.paymentAmount = transaction.getPaymentAmount();
        this.balance = transaction.getBalance();
        this.transactionDate = transaction.getTransactionTime();
    }
}
