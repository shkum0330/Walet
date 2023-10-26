package com.example.account.api.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AnimalAccountRequest {
    private String depositorName; // 예금주명
    private Long accountLimit; // 인출한도
    private String type; // 타입(일반 or 동물)
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
    private List<Long> limitTypeIdList;
}
