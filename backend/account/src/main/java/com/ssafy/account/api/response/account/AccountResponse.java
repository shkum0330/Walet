package com.ssafy.account.api.response.account;

import com.ssafy.account.db.entity.account.PetAccount;
import lombok.Data;

@Data
public class AccountResponse {
    private Long accountId;
    private String accountName;
    private String accountNumber;
    private String accountType; // 타입(일반계좌(00), 사업자계좌(01), 펫계좌(02))

    public AccountResponse(PetAccount petAccount) {
        this.accountId = petAccount.getId();
        this.accountName = petAccount.getAccountName();
        this.accountNumber = petAccount.getAccountNumber();
        this.accountType = petAccount.getAccountType().getCode();
    }
}
