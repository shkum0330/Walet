package com.example.account.db.entity;

import com.example.account.api.request.AccountRequest;
import com.example.account.api.request.AnimalAccountRequest;
import com.example.account.common.domain.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.account.db.entity.AccountState.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private String accountNumber; // 계좌번호
    private String depositorName;// 예금주명
    private Long balance = (long)0; // 잔액
    
    @Enumerated(EnumType.STRING)
    private AccountState state = ACTIVE; // 상태
    private Long accountLimit; // 인출한도
    private String type; // 타입

    private Long linkedAccountId; // 연결될 충전계좌 아이디(선택사항)

    @OneToMany(mappedBy = "myAccount")
    private List<Transaction> transactionHistory = new ArrayList<>(); // 거래내역

    // 펫 정보(일반 계좌에서는 이 값들이 null값으로 들어감)
    private String petName = null; // 펫이름
    private String petGender = null; // 펫성별
    private LocalDate petBirth = null; // 펫생년월일
    private String petType = null; // 펫종류
    private String petBreed = null; // 품종
    private Boolean petNeutered = null; // 중성화여부
    private LocalDate petRegistrationDate = null; // 등록일
    private Float petWeight = null; // 몸무게
    private String petPhoto = null; // 사진
    private String rfidCode = null;

    // 일반계좌 기본정보 입력
    public Account(AccountRequest accountRequest) {
        this.depositorName = accountRequest.getDepositorName();
        this.accountLimit = accountRequest.getAccountLimit();
        this.type = accountRequest.getType();
        this.linkedAccountId = accountRequest.getLinkedAccountId();
    }

    // 반려동물계좌 기본정보 입력
    public Account(AnimalAccountRequest accountRequest) {
        this.depositorName = accountRequest.getDepositorName();
        this.accountLimit = accountRequest.getAccountLimit();
        this.type = accountRequest.getType();
        this.linkedAccountId = accountRequest.getLinkedAccountId();
        this.petName = accountRequest.getPetName();
        this.petGender = accountRequest.getPetGender(); // 펫성별
        this.petBirth = accountRequest.getPetBirth(); // 펫생년월일
        this.petType = accountRequest.getPetType(); // 펫종류
        this.petBreed = accountRequest.getPetBreed(); // 품종
        this.petNeutered = accountRequest.getPetNeutered(); // 중성화여부
        this.petRegistrationDate = accountRequest.getPetRegistrationDate(); // 등록일
        this.petWeight = accountRequest.getPetWeight(); // 몸무게
        this.petPhoto = accountRequest.getPetPhoto(); // 사진
        this.rfidCode = accountRequest.getRfidCode();
    }

    // 계좌번호 부여
    public void createAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    // 거래내역 추가
    public void addTransaction(Transaction transaction) {
        this.transactionHistory.add(transaction);
    }
    
    // 충전계좌 추가
    public void addLinkedAccount(Long linkedAccountId) {
        this.linkedAccountId = linkedAccountId;
    }

    // 입금
    public void addBalance(Long money) {
        this.balance += money;
    }

    // 출금 및 이체
    public void minusBalance(Long money) {
        this.balance -= money;
    }

}
