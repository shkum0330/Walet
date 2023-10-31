package com.example.account.api.response;

import com.example.account.db.entity.Transaction;
import lombok.Data;

// 홈 화면에서 최근 5개 거래내역
@Data
public class HomeTransactionResponse {
    private String recipient;
    private Long paymentAmount;

    public HomeTransactionResponse(Transaction transaction){
        this.recipient = transaction.getRecipient();
        this.paymentAmount=transaction.getPaymentAmount();
    }
}
