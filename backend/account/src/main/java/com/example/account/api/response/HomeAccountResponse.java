package com.example.account.api.response;

import com.example.account.db.entity.Account;
import lombok.Data;

@Data
public class HomeAccountResponse { // 홈 화면의 계좌 정보를 나타내기 위한 DTO
    private String accountName;
    private String accountNumber;
    private Long balance;

    public HomeAccountResponse(Account account) {
        this.accountName = account.getAccountName();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
    }
}
