package com.example.account.service;

import com.example.account.api.response.HomeAccountResponse;
import com.example.account.api.response.HomeTransactionResponse;

import java.util.List;

public interface HomeAccountService {

    HomeAccountResponse getHomeAccount(Long accountId);
    List<HomeTransactionResponse> getHomeTransactions(Long accountId);
}
