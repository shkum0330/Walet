package com.example.account.service;

import com.example.account.api.request.transaction.RemittanceRequest;
import com.example.account.api.request.transaction.TransactionPeriodRequest;
import com.example.account.api.request.transaction.TransactionRequest;
import com.example.account.api.response.transaction.TransactionAccountResponse;
import com.example.account.api.response.transaction.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionAccountResponse getHomeAccountDetail(Long accountId);

    Long addPetRelatedTransaction(TransactionRequest transactionRequest);

    Long addRemittanceTransaction(RemittanceRequest remittanceRequest);
    List<TransactionResponse> getTransactionHistory(Long accountId);
    List<TransactionResponse> getSpecificPeriodTransaction(TransactionPeriodRequest request);
}
