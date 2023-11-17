package com.ssafy.auth.util;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenMapping {
    private final String accessToken;
    private final String refreshToken;
    private final String userName;

    @Builder
    public TokenMapping(String accessToken, String refreshToken, String userName){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userName = userName;
    }
}
