package com.example.account.api.request.account;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class AnimalAccountSaveRequest {
    private Long memberId;
    private String depositorName; // 예금주명
    private Long accountLimit; // 인출한도
    private String accountType; // 타입(일반(00) or 사업자(01) or 동물(02))
    private String accountPwd; // 계좌 비밀번호
    private Long linkedAccountId = null;

    private String petName; // 펫이름
    private String petGender; // 펫성별
    private LocalDate petBirth; // 펫생년월일
    private String petType; // 펫종류
    private String petBreed; // 품종
    private Boolean petNeutered; // 중성화여부
    private LocalDate petRegistrationDate; // 등록일
    private Float petWeight; // 몸무게
    private String petPhoto; // 사진
    private String rfidCode;
    private List<Integer> limitTypeIdList = new ArrayList<>();
}
