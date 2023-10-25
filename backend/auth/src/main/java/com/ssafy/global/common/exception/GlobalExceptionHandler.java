package com.ssafy.global.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalRuntimeException.class)
    public ResponseEntity<String> handleGlobalRuntimeException(GlobalRuntimeException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }
}
