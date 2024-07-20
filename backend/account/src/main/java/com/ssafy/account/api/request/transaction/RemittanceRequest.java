package com.ssafy.account.api.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemittanceRequest {
    private Long myAccountId; // 내 계좌의 PK
    private Long receiverAccountId; // 수령인 계좌의 PK
    private String password; // 내 계좌 비밀번호
    private Long remittanceAmount; // 송금액
}
