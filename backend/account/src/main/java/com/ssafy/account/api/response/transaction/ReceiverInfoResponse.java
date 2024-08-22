package com.ssafy.account.api.response.transaction;

import com.ssafy.account.db.entity.account.PetAccount;
import lombok.Data;

@Data
public class ReceiverInfoResponse {
    private Long accountId;
    private String receiverName;
    private String accountNumber;
    private Long paymentAmount;

    public ReceiverInfoResponse(PetAccount petAccount, Long paymentAmount) {
        this.accountId = petAccount.getId();
        this.receiverName = petAccount.getDepositorName();
        this.accountNumber = petAccount.getAccountNumber();
        this.paymentAmount = paymentAmount;
    }
}
