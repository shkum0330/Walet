package com.ssafy.account.service;

import com.ssafy.account.api.response.account.BusinessAccountDetailResponse;

import java.util.List;

public interface BusinessHomeAccountService {
    List<BusinessAccountDetailResponse> getBusinessAccountDetail(Long memberId);
}
