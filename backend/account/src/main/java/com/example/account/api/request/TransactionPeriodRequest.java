package com.example.account.api.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionPeriodRequest {
    private Long accountId;
    private LocalDate start;
    private LocalDate end;
}
