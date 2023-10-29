package com.example.account.common.api;

import com.example.account.common.api.status.FailCode;
import com.example.account.common.api.status.SuccessCode;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

// responseEntity 클래스
@Getter
public class Response<T> {
    private int code;
    private String message;
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

    // 성공했을 때 반환해줄 ResponseEntity
    public static <T> Response<T> success(SuccessCode code) {
        return new Response<>(code.getStatus().value(), code.getMessage());
    }

    public static <T> Response<T> success(SuccessCode code, T resultData) {

        return new Response<>(code.getStatus().value(), code.getMessage(), resultData);
    }

    // 실패했을 때 반환해줄 ResponseEntity
    public static <T> Response<T> fail(FailCode code) {
        return new Response<>(code.getStatus().value(), code.getMessage());
    }
    
}
