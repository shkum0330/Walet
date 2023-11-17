package com.ssafy.global.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(GlobalRuntimeException.class)
    public ResponseEntity<Response> handleGlobalRuntimeException(GlobalRuntimeException exception) {
        return Response.fail(exception.getFailCode());
    }
}
