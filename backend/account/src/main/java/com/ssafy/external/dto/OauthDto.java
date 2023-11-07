package com.ssafy.external.dto;

import lombok.Getter;
import lombok.Setter;

public class OauthDto {
    @Setter
    @Getter
    public static class Response{
        String message;
        String code;
        String data;
    }
}
