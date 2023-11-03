package com.ssafy.account.common.api.exception;

import com.ssafy.account.common.api.status.FailCode;
import lombok.Getter;

@Getter
public class RestrictedBusinessException extends RuntimeException {
    private final FailCode failCode;

    public RestrictedBusinessException(FailCode failCode) {
        super(failCode.getMessage());
        this.failCode = failCode;
    }
}
