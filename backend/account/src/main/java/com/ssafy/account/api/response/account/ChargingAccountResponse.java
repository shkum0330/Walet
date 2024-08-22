package com.ssafy.account.api.response.account;

import com.ssafy.account.db.entity.account.PetAccount;
import lombok.Data;

@Data
public class ChargingAccountResponse { // 충전계좌
    private Long accountId;
    private String accountName; // 계좌명(ex. NH올원e예금)
    private String accountNumber; // 계좌번호
    private Long balance; // 잔액

    public ChargingAccountResponse(PetAccount petAccount) {
        this.accountId = petAccount.getId();
        this.accountName = petAccount.getAccountName();
        this.accountNumber = petAccount.getAccountNumber();
        this.balance = petAccount.getBalance();
    }
}
