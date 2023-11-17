package com.ssafy.global.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    GENERAL_SUCCESS(OK, "데이터 처리 성공"),
    CREATE_SUCCESS(CREATED, "데이터 생성 성공"),
    DELETE_SUCCESS(NO_CONTENT, "데이터 삭제 성공");

    private final HttpStatus status;
    private final String message;
}