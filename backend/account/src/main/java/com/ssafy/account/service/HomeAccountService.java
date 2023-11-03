package com.ssafy.account.service;

import com.ssafy.account.api.response.account.HomeAccountResponse;

import java.util.List;

public interface HomeAccountService {
    List<HomeAccountResponse> getHomeAccountDetail(Long memberId); // 계좌 메인페이지에서 일반계좌 기본정보와 최근 거래목록 5개를 보여줌
}
