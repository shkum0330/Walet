package com.ssafy.account.service;

import com.ssafy.account.api.response.account.HomeAccountResponse;

public interface HomeAccountService {
    HomeAccountResponse getHomeAccountDetail(Long accountId); // 계좌 메인페이지에서 계좌 기본정보와 최근 거래목록 5개를 보여줌
}
