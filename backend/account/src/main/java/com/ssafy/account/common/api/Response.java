package com.ssafy.account.common.api;

import com.ssafy.account.common.api.status.FailCode;
import com.ssafy.account.common.api.status.SuccessCode;
import lombok.Getter;

// responseEntity 클래스
@Getter
public class Response<T> {
    private int code;
    private final String message;
    private T data;

    public Response(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> Response<T> success(SuccessCode code, T resultData) {
        return new Response<>(code.getStatus().value(), code.getMessage(), resultData);
    }

}
