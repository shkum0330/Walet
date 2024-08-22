package com.ssafy.account.api.response.account;

import com.ssafy.account.db.entity.account.PetAccount;
import lombok.Data;

@Data
public class AdminMemberAccountResponse {
    private Long accountId; // 계좌 PK
    private Long memberId; // 계좌 주인의 PK
    private String accountName; // 계좌명
    private String accountNumber; // 계좌번호
    private Long balance; // 계좌 잔액

    public AdminMemberAccountResponse(PetAccount petAccount) {
        this.accountId = petAccount.getId();
        this.memberId = petAccount.getMemberId();
        this.accountName = petAccount.getAccountName();
        this.accountNumber = petAccount.getAccountNumber();
        this.balance = petAccount.getBalance();
    }
}
