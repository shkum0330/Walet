package com.ssafy.auth.api;

import lombok.Getter;
import lombok.Setter;

public class PinCheckDto {
    @Getter @Setter
    public static class RequestDto {
        private String pinNumber;
    }
}
