package com.ssafy.account.api.response.transaction;

import com.ssafy.account.db.entity.transaction.Transaction;
import lombok.Data;

// 홈 화면에서 최근 5개 거래내역
@Data
public class HomeTransactionResponse {
    private String sender; // 발신인
    private String recipient; // 수령인
    private Long paymentAmount; // 금액
    private String transactionType; // 거래 종류

    public HomeTransactionResponse(Transaction transaction){
        this.sender = transaction.getAccount().getDepositorName();
        this.recipient = transaction.getRecipient();
        this.paymentAmount=transaction.getPaymentAmount();
        this.transactionType = transaction.getTransactionType().getName();
    }
}
