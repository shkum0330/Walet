package com.ssafy.account.api.response.transaction;

import com.ssafy.account.db.entity.account.Account;
import lombok.Data;

@Data
public class TransactionAccountResponse {
    private Long accountId;
    private String accountName;
    private String accountNumber;
    private Long balance;

    public TransactionAccountResponse(Account account) {
        this.accountId = account.getId();
        this.accountName = account.getAccountName();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
    }
}
