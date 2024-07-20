package com.ssafy.account.api.response.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminAccountCountResponse {
    private Long generalAccountCount; // 전체 일반계좌 수
    private Long petAccountCount; // 전체 펫계좌 수
}
