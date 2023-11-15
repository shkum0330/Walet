package com.ssafy.global.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 예외를 처리해주는 클래스
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(GlobalRuntimeException.class)
    public Response handleGlobalRuntimeException(GlobalRuntimeException exception) {
        return Response.fail(exception.getFailCode());
    }

}
