package com.ssafy.account.common.api;

import com.ssafy.account.common.api.status.FailCode;
import lombok.Getter;

@Getter
public class FailResponse {

    private int code;
    private String message;

    public FailResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // 실패했을 때 반환해줄 ResponseEntity
    public static FailResponse fail(FailCode code) {
        return new FailResponse(code.getStatus().value(), code.getMessage());
    }
}
