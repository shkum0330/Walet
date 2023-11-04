package com.ssafy.account.api.response.account;

import com.ssafy.account.api.response.transaction.HomeTransactionResponse;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.entity.account.AccountState;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AnimalAccountDetailResponse {

    private Long id;
    private String accountName;
    private String accountNumber; // 계좌번호
    private Long balance; // 잔액
    private AccountState state; // 상태
    private LocalDateTime createdAt; // 등록일
    private Long accountLimit; // 인출한도
    private String accountType; // 타입

    private Long linkedAccountId; // 연결된 모계좌 id

    private String petName; // 펫이름
    private String petGender; // 펫성별
    private LocalDate petBirth; // 펫생년월일
    private String petType; // 펫종류
    private String petBreed; // 품종
    private Boolean petNeutered; // 중성화여부
    private LocalDate petRegistrationDate; // 등록일
    private Float petWeight; // 몸무게
    private String petPhoto; // 사진
    private List<HomeTransactionResponse> transactions;

    public AnimalAccountDetailResponse(Account account, List<HomeTransactionResponse> transactions) {
        this.id = account.getId();
        this.accountName = account.getAccountName();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
        this.state = account.getState();
        this.createdAt = account.getCreatedAt();
        this.accountLimit = account.getAccountLimit();
        this.accountType = account.getAccountType();
        this.linkedAccountId = account.getLinkedAccountId();
        this.petName = account.getPetName();
        this.petGender = account.getPetGender();
        this.petBirth = account.getPetBirth();
        this.petBreed = account.getPetBreed();
        this.petNeutered = account.getPetNeutered();
        this.petRegistrationDate = account.getPetRegistrationDate();
        this.petWeight = account.getPetWeight();
        this.petPhoto = account.getPetPhoto();
        this.transactions = new ArrayList<>(transactions);
    }
}
