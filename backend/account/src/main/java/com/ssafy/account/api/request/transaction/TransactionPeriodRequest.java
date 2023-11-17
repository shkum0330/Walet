package com.ssafy.account.api.request.transaction;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionPeriodRequest {
    private Long accountId;
    private LocalDate start;
    private LocalDate end;

    public TransactionPeriodRequest(Long accountId, LocalDate start, LocalDate end) {
        this.accountId = accountId;
        this.start = start;
        this.end = end;
    }
}
