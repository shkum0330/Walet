package com.example.account.service;

import com.example.account.api.response.account.AnimalAccountDetailResponse;

public interface PetHomeAccountService {
    // 반려동물계좌 관련 정보 반환
    AnimalAccountDetailResponse getAnimalAccountDetail(Long accountId);
}
