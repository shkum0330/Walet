package com.ssafy.account.api.response.account;

import com.ssafy.account.api.response.transaction.HomeTransactionResponse;
import com.ssafy.account.db.entity.account.PetAccount;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AnimalAccountDetailResponse {

    private Long id;
    private String accountName; // 계좌명(ex. NH올원e예금)
    private String accountNumber; // 계좌번호
    private Long balance; // 잔액
    private String accountState; // 상태
    private LocalDateTime createdAt; // 계좌생성일
    private String accountType; // 계좌타입

    private Long linkedAccountId; // 연결된 충전계좌 id
    private String linkedAccountNumber; // 연결된 충전계좌번호

    private String petName; // 펫이름
    private String petGender; // 펫성별
    private String petAge; // 펫 나이
    private String petBreed; // 품종
    private Boolean petNeutered; // 중성화여부
    private Float petWeight; // 몸무게
    private String petPhoto; // 사진
    private List<HomeTransactionResponse> transactions;

    public AnimalAccountDetailResponse(PetAccount petAccount, List<HomeTransactionResponse> transactions, String petAge, String linkedAccountNumber) {
        this.id = petAccount.getId();
        this.accountName = petAccount.getAccountName();
        this.accountNumber = petAccount.getAccountNumber();
        this.balance = petAccount.getBalance();
        this.accountState = petAccount.getAccountState().getCode();
        this.createdAt = petAccount.getCreatedAt();
        this.accountType = petAccount.getAccountType().getCode();
        this.linkedAccountId = petAccount.getLinkedAccountId();
        this.linkedAccountNumber = linkedAccountNumber;
        this.petName = petAccount.getPetName();
        this.petGender = petAccount.getPetGender();
        this.petAge = petAge;
        this.petBreed = petAccount.getPetBreed();
        this.petNeutered = petAccount.getPetNeutered();
        this.petWeight = petAccount.getPetWeight();
        this.petPhoto = petAccount.getPetPhoto();
        this.transactions = new ArrayList<>(transactions);
    }
}
