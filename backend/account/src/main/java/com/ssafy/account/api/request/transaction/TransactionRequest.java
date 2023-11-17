package com.ssafy.account.api.request.transaction;

import lombok.Data;

@Data
public class TransactionRequest {
    // Todo. buyerId를 삭제하고, rfid로 반려동물계좌를 특정지을것
    private Long myAccountId; // 구매자의 계좌 id
    private Long companyAccountId; // 사업자의 계좌 id
    private Long paymentAmount; // 거래 금액

    public TransactionRequest(Long myAccountId, Long companyAccountId, Long paymentAmount) {
        this.myAccountId = myAccountId;
        this.companyAccountId = companyAccountId;
        this.paymentAmount = paymentAmount;
    }
}
