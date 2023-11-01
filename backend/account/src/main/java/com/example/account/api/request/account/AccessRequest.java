package com.example.account.api.request.account;

import lombok.Data;

import javax.persistence.Column;

@Data
public class AccessRequest {
    private String animalName; // 상대방 반려동물 이름
    private String accountNumber; // 접근신청할 계좌번호
    private String content; // 상대방에게 보낼 메시지
    private String randomMemberId; // 접근요청한 사람의 난수회원ID
}

