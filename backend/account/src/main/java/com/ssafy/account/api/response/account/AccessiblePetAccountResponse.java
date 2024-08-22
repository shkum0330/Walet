package com.ssafy.account.api.response.account;

import com.ssafy.account.db.entity.account.PetAccount;
import lombok.Data;


@Data
public class AccessiblePetAccountResponse {
    private Long id;
    private String accountName;
    private String accountNumber; // 계좌번호
    private Long balance; // 잔액
    private String petName; // 펫이름
    private String petPhoto; // 사진

    public AccessiblePetAccountResponse(PetAccount petAccount) {
        this.id = petAccount.getId();
        this.accountName = petAccount.getAccountName();
        this.accountNumber = petAccount.getAccountNumber();
        this.balance = petAccount.getBalance();
        this.petName = petAccount.getPetName();
        this.petPhoto = petAccount.getPetPhoto();
    }
}
