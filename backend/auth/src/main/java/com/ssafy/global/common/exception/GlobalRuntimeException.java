package com.ssafy.global.common.exception;

import com.ssafy.global.common.status.FailCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalRuntimeException extends RuntimeException {

    private final FailCode failCode;

    public GlobalRuntimeException(FailCode failCode) {
        super(failCode.getMessage());
        this.failCode = failCode;
    }
}