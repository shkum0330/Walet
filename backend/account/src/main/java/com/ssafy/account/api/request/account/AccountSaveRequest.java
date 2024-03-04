package com.ssafy.account.api.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountSaveRequest {
    private String accountName; // 계좌명(ex. NH올원e예금)
    private String accountType; // 타입(일반(00), 사업자(01), 펫(02))
    private Integer businessType; // 사업자계좌면 사업유형도 입력
    private String accountPassword; // 계좌 비밀번호
    private Long linkedAccountId; // 충전계좌를 선택했다면 해당 계좌의 아이디도 보내줌
}
