package com.ssafy.auth.service;

import com.ssafy.auth.util.TokenMapping;

public interface UserRepository {
    TokenMapping login(String email, String password);
}