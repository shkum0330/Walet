package com.ssafy.global.common.response;

import com.ssafy.global.common.status.SuccessCode;
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

    public EnvelopeResponse(SuccessCode successCode, T data) {
        this.code = successCode.getStatus().value();
        this.message = successCode.getMessage();
        this.data = data;
    }
}
