package com.ssafy.api_gateway.error.exception;

import com.ssafy.api_gateway.error.ErrorCode;

public class AuthenticationException extends BusinessException{

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
