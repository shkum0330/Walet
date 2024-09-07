package com.ssafy.account.api.response.account;

import com.ssafy.account.api.response.transaction.HomeTransactionResponse;
import com.ssafy.account.db.entity.account.Account;
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

    public AnimalAccountDetailResponse(Account account, List<HomeTransactionResponse> transactions, String petAge, String linkedAccountNumber) {
        this.id = account.getId();
        this.accountName = account.getAccountName();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
        this.accountState = account.getAccountState().getCode();
        this.createdAt = account.getCreatedAt();
        this.accountType = account.getAccountType().getCode();
        this.linkedAccountId = account.getLinkedAccountId();
        this.linkedAccountNumber = linkedAccountNumber;
        this.petName = account.getPetName();
        this.petGender = account.getPetGender();
        this.petAge = petAge;
        this.petBreed = account.getPetBreed();
        this.petNeutered = account.getPetNeutered();
        this.petWeight = account.getPetWeight();
        this.petPhoto = account.getPetPhoto();
        this.transactions = new ArrayList<>(transactions);
    }
}
