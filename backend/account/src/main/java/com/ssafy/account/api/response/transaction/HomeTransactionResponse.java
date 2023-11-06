package com.ssafy.account.api.response.transaction;

import com.ssafy.account.db.entity.transaction.Transaction;
import lombok.Data;

// 홈 화면에서 최근 5개 거래내역
@Data
public class HomeTransactionResponse {
    private String recipient; // 수령인(우선은 거래내역 중 사용자가 돈을 보낸 상황만 고려)
    private Long paymentAmount; // 금액

    public HomeTransactionResponse(Transaction transaction){
        this.recipient = transaction.getRecipient();
        this.paymentAmount=transaction.getPaymentAmount();
    }
}
