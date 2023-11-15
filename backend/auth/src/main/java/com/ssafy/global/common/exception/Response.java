package com.ssafy.global.common.exception;

import com.ssafy.global.common.status.FailCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class Response {
    private int code;
    private String message;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseEntity<Response> fail(FailCode code) {
        return ResponseEntity.status(code.getStatus()).body(new Response(code.getStatus().value(), code.getMessage()));
    }
}
