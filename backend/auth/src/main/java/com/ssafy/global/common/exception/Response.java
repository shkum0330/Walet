package com.ssafy.global.common.exception;


import com.ssafy.global.common.status.FailCode;
import lombok.Getter;

// responseEntity 클래스
@Getter
public class Response<T> {
    private int code;
    private String message;


    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public static <T> Response<T> fail(FailCode code) {
        return new Response<>(code.getStatus().value(), code.getMessage());
    }
    
}
