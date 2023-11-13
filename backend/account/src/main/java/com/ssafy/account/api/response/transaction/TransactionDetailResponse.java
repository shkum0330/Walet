package com.ssafy.account.api.response.transaction;

import com.ssafy.account.db.entity.transaction.Transaction;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class TransactionDetailResponse {
    private String recipient; // 거래대상
    private String businessCategory; // 거래한 회사의 카테고리
    private Long paymentAmount; // 거래 금액
    private Long balance; // 거래 후 잔액
    private String transactionTime; // 거래시간
    private String phoneNumber; // 전화번호

    public TransactionDetailResponse(Transaction transaction, String businessCategory, String phoneNumber) {
        this.recipient = transaction.getRecipient();
        this.businessCategory = businessCategory;
        this.paymentAmount = transaction.getPaymentAmount();
        this.balance = transaction.getBalance();
        this.phoneNumber = phoneNumber;
        if (transaction.getTransactionTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
            this.transactionTime = formatter.format(transaction.getTransactionTime());
        }
    }
}
