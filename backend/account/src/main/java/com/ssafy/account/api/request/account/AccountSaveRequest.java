package com.ssafy.account.api.request.account;

import lombok.Data;

@Data
public class AccountSaveRequest {
    private Long memberId;
    private String accountName; // 예금 종류
    private String depositorName; // 예금주명
    private Long accountLimit; // 인출한도
    private String accountType; // 타입(일반 or 동물)
    private Integer businessType; // 사업자계좌면 사업유형도 입력
    private String accountPwd; // 계좌 비밀번호
    private Long linkedAccountId;
}
