package com.example.account.service;

import com.example.account.api.request.TransactionPeriodRequest;
import com.example.account.api.request.TransactionRequest;
import com.example.account.api.response.TransactionAccountResponse;
import com.example.account.api.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionAccountResponse getHomeAccountDetail(Long accountId);

    Long addTransaction(TransactionRequest transactionRequest);
    List<TransactionResponse> getTransactionHistory(Long accountId);
    List<TransactionResponse> getSpecificPeriodTransaction(TransactionPeriodRequest request);
}
