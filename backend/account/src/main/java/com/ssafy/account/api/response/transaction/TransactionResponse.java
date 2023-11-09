package com.ssafy.account.api.response.transaction;

import com.ssafy.account.db.entity.transaction.Transaction;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponse {

    private Long id;
    private String counterPart;

    private String transactionType; // 거래 타입
    private Long paymentAmount; // 거래 금액
    private Long balance; // 거래 후 잔액
    private LocalDateTime transactionDate; // 거래일자

    public TransactionResponse(String counterPart, Transaction transaction) {
        this.id = transaction.getId();
        this.counterPart = counterPart;
        this.transactionType = transaction.getTransactionType().getName();
        this.paymentAmount = transaction.getPaymentAmount();
        this.balance = transaction.getBalance();
        this.transactionDate = transaction.getTransactionTime();
    }
}
