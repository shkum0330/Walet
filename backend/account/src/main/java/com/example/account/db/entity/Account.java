package com.example.account.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber; // 계좌번호
    private Long balance; // 잔액
    private boolean state; // 상태
    private LocalDate createdAt; // 등록일
    private Long accountLimit; // 인출한도
    private int type; // 타입

    @OneToOne
    @JsonIgnore
    private Account linkedAccount; // 연결될 모계좌(선택사항)

    // 펫 정보
    private String petName; // 펫이름
    private String petGender; // 펫성별
    private LocalDate petBirth; // 펫생년월일
    private String petType; // 펫종류
    private String petBreed; // 품종
    private boolean petNeutered; // 중성화여부
    private LocalDate petCreatedDate; // 등록일
    private int petWeight; // 몸무게
    private String petColor; // 색상
    private String petPhoto; // 사진
}
