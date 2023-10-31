package com.example.account.service;

import com.example.account.api.response.account.HomeAccountResponse;

public interface HomeAccountService {
    HomeAccountResponse getHomeAccountDetail(Long accountId);
}
