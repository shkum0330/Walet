package com.ssafy.account.common.api;

import com.ssafy.account.common.api.status.FailCode;
import com.ssafy.account.common.api.status.SuccessCode;
import org.springframework.http.HttpStatus;

/**
 * REST API Response
 *
 * @author Sehyun Kum
 * @version 1.0
 */
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

    public static <T> Response<T> ok(SuccessCode code, T resultData) {
        return new Response<>(code.getStatus().value(), code.getMessage(), resultData);
    }

    public static <T> Response<T> created(SuccessCode code, T resultData) {
        return new Response<>(code.getStatus().value(), code.getMessage(), resultData);
    }

    public static <T> Response<T> notFound(FailCode code, T resultData) {
        return new Response<>(code.getStatus().value(), code.getMessage(), resultData);
    }

    public static <T> Response<T> forbidden(FailCode code, T resultData) {
        return new Response<>(code.getStatus().value(), code.getMessage(), resultData);
    }

    public static <T> Response<T> badRequest(FailCode code, T resultData) {
        return new Response<>(code.getStatus().value(), code.getMessage(), resultData);
    }

    public static <T> Response<T> internalServerError(FailCode code, T resultData) {
        return new Response<>(code.getStatus().value(), code.getMessage(), resultData);
    }

}
