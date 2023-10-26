package com.example.account.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyExpenditureDetailResponse {
    // 매달 지출내역
    private Long currentTotalExpenditure; // 이번 달에서 현재까지의 총 지출액
    private Map<Integer, Long> expenditureRatio; // 전체 지출에서 제한업종 별 지출액이 차지하는 비중

    // 이전 지출내역: 이전 달의 총 지출액, 현재 총 지출액과의 차이
    private Long lastMonthTotalExpenditure; // 이번 달에서 현재까지의 총 지출액
    private Long growthRate; // 이전 달 대비 현재의 총 지출 증감율
}
