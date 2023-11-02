package com.ssafy.account.api.response.account;

import com.ssafy.account.db.entity.account.Account;
import lombok.Data;

@Data
public class ChargingAccountResponse { // 충전계좌
    private Long accountId;
    private String accountName; // 계좌명(ex. NH올원e예금)
    private String accountNumber; // 계좌번호

    public ChargingAccountResponse(Account account) {
        this.accountId = account.getId();
        this.accountName = account.getAccountName();
        this.accountNumber = account.getAccountNumber();
    }
}
