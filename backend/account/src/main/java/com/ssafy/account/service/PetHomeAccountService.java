package com.ssafy.account.service;

import com.ssafy.account.api.response.account.AnimalAccountDetailResponse;

public interface PetHomeAccountService {
    // 반려동물계좌 관련 정보 반환
    AnimalAccountDetailResponse getAnimalAccountDetail(Long accountId); // 반려동물계좌 메인페이지에서 계좌 기본정보와 최근 거래목록 5개를 보여줌
}
