package com.ssafy.account.api.request.transaction;

import com.ssafy.account.db.entity.transaction.TransactionType;
import lombok.Data;

@Data
public class TransactionRequest {
    private String rfidCode;
    // Todo. buyerId를 삭제하고, rfid로 반려동물계좌를 특정지을것
    private Long buyerId; // 구매자의 memberId
    private Long companyAccountId; // 이체할 회사의 계좌번호
    private TransactionType transactionType; // 거래 타입
    private Long paymentAmount; // 거래 금액

    public TransactionRequest(String rfidCode, Long buyerId, Long companyAccountId, TransactionType transactionType, Long paymentAmount) {
        this.rfidCode = rfidCode;
        this.buyerId = buyerId;
        this.companyAccountId = companyAccountId;
        this.transactionType = transactionType;
        this.paymentAmount = paymentAmount;
    }

}
