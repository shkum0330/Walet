package com.ssafy.account.api.request.access;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessSaveRequest {
    private String petName; // 상대방 반려동물 이름
    private String accountNumber; // 접근신청할 계좌번호
    private String content; // 상대방에게 보낼 메시지
}

