package com.example.account.api.request;

import lombok.Data;

@Data
public class AccountRequest {
    private String depositorName; // 예금주명
    private Long accountLimit; // 인출한도
    private String type; // 타입(일반 or 동물)
    private Long linkedAccountId = null;
}
