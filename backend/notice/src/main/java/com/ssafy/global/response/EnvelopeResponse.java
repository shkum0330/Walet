package com.ssafy.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class EnvelopeResponse<T> {

    @Builder.Default
    private String message = "";
    private int code;
    private T data;

    public EnvelopeResponse(int code, String message, T data) {
        this.message = message;
        this.data = data;
        this.code = code;
    }
}
