package com.ssafy.auth.util;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenMapping {
    private final String accessToken;
    private final String refreshToken;
    private final String username;

    @Builder
    public TokenMapping(String accessToken, String refreshToken, String username){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
    }
}
