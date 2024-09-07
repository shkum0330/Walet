package com.ssafy.account.api.response.account;

import com.ssafy.account.db.entity.account.Account;
import lombok.Data;

@Data
public class TransferAccountResponse {
    private Long accountId;
    private String accountName;
    private String accountNumber;
    private String accountType; // 타입(일반계좌(00), 사업자계좌(01), 펫계좌(02))
    private Long balance;

    public TransferAccountResponse(Account account) {
        this.accountId = account.getId();
        this.accountName = account.getAccountName();
        this.accountNumber = account.getAccountNumber();
        this.accountType = account.getAccountType().getCode();
        this.balance= account.getBalance();
    }
}
