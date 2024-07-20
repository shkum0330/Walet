package com.ssafy.account.common.api.exception;

import com.ssafy.account.common.api.status.FailCode;
import lombok.Getter;

@Getter
public class FileNotFoundException extends RuntimeException{
    private final FailCode failCode;

    public FileNotFoundException(FailCode failCode) {
        super(failCode.getMessage());
        this.failCode = failCode;
    }
}
