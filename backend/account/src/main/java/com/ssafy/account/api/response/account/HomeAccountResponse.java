package com.ssafy.account.api.response.account;

import com.ssafy.account.api.response.transaction.HomeTransactionResponse;
import com.ssafy.account.db.entity.account.PetAccount;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HomeAccountResponse { // 홈 화면의 계좌 정보를 나타내기 위한 DTO
    private Long accountId;
    private String accountName;
    private String accountNumber;
    private Long balance;
    private List<HomeTransactionResponse> transactions; // 최근 5개 거래목록

    public HomeAccountResponse(PetAccount petAccount, List<HomeTransactionResponse> transactions) {
        this.accountId = petAccount.getId();
        this.accountName = petAccount.getAccountName();
        this.accountNumber = petAccount.getAccountNumber();
        this.balance = petAccount.getBalance();
        this.transactions = new ArrayList<>(transactions);
    }
}
