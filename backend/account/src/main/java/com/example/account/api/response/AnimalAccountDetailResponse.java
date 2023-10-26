package com.example.account.api.response;

import com.example.account.db.entity.Account;
import com.example.account.db.entity.AccountState;
import com.example.account.db.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AnimalAccountDetailResponse {

    private Long id;
    private String accountNumber; // 계좌번호
    private Long balance; // 잔액
    private AccountState state; // 상태
    private LocalDateTime createdAt; // 등록일
    private Long accountLimit; // 인출한도
    private String type; // 타입

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

    // 최신 거래 내역 5개
    private List<Transaction> recentTransactions; // 거래내역

    public AnimalAccountDetailResponse(Account account, List<Transaction> recentTransactions) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
        this.state = account.getState();
        this.createdAt = account.getCreatedAt();
        this.accountLimit = account.getAccountLimit();
        this.type = account.getType();
        this.linkedAccountId = account.getLinkedAccountId();
        this.petName = account.getPetName();
        this.petGender = account.getPetGender();
        this.petBirth = account.getPetBirth();
        this.petType = account.getPetType();
        this.petBreed = account.getPetBreed();
        this.petNeutered = account.getPetNeutered();
        this.petRegistrationDate = account.getPetRegistrationDate();
        this.petWeight = account.getPetWeight();
        this.petPhoto = account.getPetPhoto();
        this.recentTransactions = new ArrayList<>(recentTransactions);
    }
}
