package com.ssafy.account.api.response.account;

import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.account.AccountState;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AccessiblePetAccountResponse {
    private Long id;
    private String accountName;
    private String accountNumber; // 계좌번호
    private Long balance; // 잔액
    private String petName; // 펫이름
    private String petPhoto; // 사진

    public AccessiblePetAccountResponse(Account account) {
        this.id = account.getId();
        this.accountName = account.getAccountName();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
        this.petName = account.getPetName();
        this.petPhoto = account.getPetPhoto();
    }
}
