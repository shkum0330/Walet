package com.ssafy.member.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class VerificationDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class PhoneRequest{
        private String phoneNumber;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class CodeRequest{
        private String phoneNumber;
        private String code;
    }
}
