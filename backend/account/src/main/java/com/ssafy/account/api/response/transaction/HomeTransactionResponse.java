package com.ssafy.account.api.response.transaction;

import com.ssafy.account.db.entity.transaction.Transaction;
import lombok.Data;

// 홈 화면에서 최근 5개 거래내역
@Data
public class HomeTransactionResponse {
    private String counterPart; // 거래한 상대방이름
    private Long paymentAmount; // 금액
    private String transactionType; // 거래 종류


    public HomeTransactionResponse(Transaction transaction, String counterPart){
        this.counterPart = counterPart;
        this.paymentAmount=transaction.getPaymentAmount();
        this.transactionType = transaction.getTransactionType().getName();
    }
}
