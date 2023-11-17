package com.ssafy.account.common.api.exception;

import com.ssafy.account.common.api.status.FailCode;
import lombok.Getter;

@Getter
public class InvalidPaymentException extends RuntimeException{

    private final FailCode failCode;

    public InvalidPaymentException(FailCode failCode) {
        super(failCode.getMessage());
        this.failCode = failCode;
    }
}
