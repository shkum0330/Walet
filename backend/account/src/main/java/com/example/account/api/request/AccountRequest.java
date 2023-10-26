package com.example.account.api.request;

import lombok.Data;

@Data
public class AccountRequest {
    private String depositorName; // 예금주명
    private Long accountLimit; // 인출한도
    private boolean accountType; // 타입(사업자 or 동물)
    private Integer businessType = null; // 사업자계좌면 사업유형도 입력
    private Long linkedAccountId = null;
}
