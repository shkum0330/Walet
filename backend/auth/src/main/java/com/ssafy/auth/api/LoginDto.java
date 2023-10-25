package com.ssafy.auth.api;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDto {
    private String email;
    private String password;
}
