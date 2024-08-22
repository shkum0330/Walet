package com.ssafy.account.api.response.transaction;

import com.ssafy.account.db.entity.account.PetAccount;
import lombok.Data;

@Data
public class TransactionAccountResponse {
    private Long accountId;
    private String accountName;
    private String accountNumber;
    private Long balance;

    public TransactionAccountResponse(PetAccount petAccount) {
        this.accountId = petAccount.getId();
        this.accountName = petAccount.getAccountName();
        this.accountNumber = petAccount.getAccountNumber();
        this.balance = petAccount.getBalance();
    }
}
