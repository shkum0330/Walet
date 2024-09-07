package com.ssafy.account.api.response.account;

import com.ssafy.account.db.entity.account.Account;
import lombok.Data;

@Data
public class AdminMemberAccountResponse {
    private Long accountId; // 계좌 PK
    private Long memberId; // 계좌 주인의 PK
    private String accountName; // 계좌명
    private String accountNumber; // 계좌번호
    private Long balance; // 계좌 잔액

    public AdminMemberAccountResponse(Account account) {
        this.accountId = account.getId();
        this.memberId = account.getMemberId();
        this.accountName = account.getAccountName();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
    }
}
