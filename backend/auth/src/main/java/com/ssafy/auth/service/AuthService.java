package com.ssafy.auth.service;

import com.ssafy.auth.util.TokenMapping;

public interface AuthService {
    TokenMapping login(String email, String password);
    TokenMapping adminLogin(String email, String password);
    void logout(String accessToken);
}