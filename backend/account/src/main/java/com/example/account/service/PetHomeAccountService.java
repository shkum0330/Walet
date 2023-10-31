package com.example.account.service;

import com.example.account.api.response.AnimalAccountDetailResponse;
import com.example.account.api.response.HomeTransactionResponse;

import java.util.List;

public interface PetHomeAccountService {
    // 반려동물계좌 관련 정보 반환
    AnimalAccountDetailResponse getAnimalAccountDetail(Long accountId);
}
