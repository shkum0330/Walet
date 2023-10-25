package com.ssafy.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Getter
public class EnvelopeResponse<T> {


    @Builder.Default
    private String message = "ok";

    private T data;

    public EnvelopeResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
