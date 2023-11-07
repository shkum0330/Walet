package com.ssafy.service.external.dto;

import lombok.Getter;
import lombok.Setter;

public class OauthDto {
    @Setter
    @Getter
    public static class Response{
        String massage;
        String code;
        String data;
    }
}
