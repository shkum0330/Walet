package com.ssafy.external.service;

public interface OauthService {
    String getOauthKey();

    String getUserName(Long memberId);

    String getUserPhoneNumber(Long memberId);
}
