package com.ssafy.account.api.response.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyExpenditureDetailResponse {
    private Long currentMonthTotalExpenditure; // 이번 달에서 현재까지의 총 지출액
    private Map<String, Long> currentExpenditureByCategory; // 이번 달 제한업종 별 지출액
    private Map<String, Long> lastMonthExpenditureByCategory; // 이전 달 제한업종 별 지출액
    private Map<String, Long> expenditureChange; // 이전 달 대비 현재의 제한업종 별 지출액 증감량
}
