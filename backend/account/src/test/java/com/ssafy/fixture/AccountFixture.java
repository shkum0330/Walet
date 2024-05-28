package com.ssafy.fixture;

import com.ssafy.account.db.entity.account.Account;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
public enum AccountFixture {

    NORMAL(1L,1L,"일반계좌","2987993781759","김민지","1234",10000000L,"00","00",null,null,null
            ,null,null,null,null,null,null,null,null,null),
    BUSINESS(2L,1L,"사업자계좌","5467807340455","김민지","1234",10000000L,"00","01",1,null,null
            ,null,null,null,null,null,null,null,null,null),
    PET(2L,1L,"펫계좌","0035791444046","김민지","1234",10000000L,"00","02",null,null,
            "꼬맹이","강아지","남아",LocalDate.of(2012,1,11),"말티즈",true,4.06f,null,"RFID1234",17);
    private Long id;
    private Long memberId;
    private String accountName;
    private String accountNumber;
    private String depositorName;
    private String accountPassword;
    private Long balance;
    private String accountState; // 상태 => 정상(00), 잠금(01), 정지(10), 폐쇄(11)
    private String accountType; // 타입(일반계좌(00), 사업자계좌(01), 펫계좌(02))
    private Integer businessType;
    private Long linkedAccountId;
    private String petName;
    private String petType;
    private String petGender;
    private LocalDate petBirth;
    private String petBreed;
    private Boolean petNeutered;
    private Float petWeight;
    private String petPhoto;
    private String rfidCode;
    private Integer limitTypes=0;

    AccountFixture(Long id, Long memberId, String accountName, String accountNumber, String depositorName,
                   String accountPassword, Long balance, String accountState, String accountType, Integer businessType, Long linkedAccountId,
                   String petName, String petType, String petGender, LocalDate petBirth, String petBreed, Boolean petNeutered, Float petWeight, String petPhoto, String rfidCode,
                   Integer limitTypes) {
        this.id = id;
        this.memberId = memberId;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.depositorName = depositorName;
        this.accountPassword = accountPassword;
        this.balance = balance;
        this.accountState = accountState;
        this.accountType = accountType;
        this.businessType = businessType;
        this.linkedAccountId = linkedAccountId;
        this.petName = petName;
        this.petType = petType;
        this.petGender = petGender;
        this.petBirth = petBirth;
        this.petBreed = petBreed;
        this.petNeutered = petNeutered;
        this.petWeight = petWeight;
        this.petPhoto = petPhoto;
        this.rfidCode = rfidCode;
        this.limitTypes = limitTypes;
    }

    public Account getAccount(){
        return Account.builder()
                .id(id)
                .memberId(memberId)
                .accountName(accountName)
                .accountNumber(accountNumber)
                .depositorName(depositorName)
                .accountPassword(accountPassword)
                .balance(balance)
                .accountState(accountState)
                .accountType(accountType)
                .businessType(businessType)
                .linkedAccountId(linkedAccountId)
                .petName(petName)
                .petType(petType)
                .petGender(petGender)
                .petBirth(petBirth)
                .petBreed(petBreed)
                .petNeutered(petNeutered)
                .petWeight(petWeight)
                .petPhoto(petPhoto)
                .rfidCode(rfidCode)
                .limitTypes(limitTypes)
                .build();
    }
}
