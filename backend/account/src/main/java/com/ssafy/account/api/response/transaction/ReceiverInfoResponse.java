package com.ssafy.account.api.response.transaction;

import com.ssafy.account.db.entity.account.Account;
import lombok.Data;

@Data
public class ReceiverInfoResponse {
    private Long accountId;
    private String receiverName;
    private String accountNumber;
    private Long paymentAmount;

    public ReceiverInfoResponse(Account account, Long paymentAmount) {
        this.accountId = account.getId();
        this.receiverName = account.getDepositorName();
        this.accountNumber = account.getAccountNumber();
        this.paymentAmount = paymentAmount;
    }
}
