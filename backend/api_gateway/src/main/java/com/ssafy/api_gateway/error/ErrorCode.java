package com.ssafy.api_gateway.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED , 401 , "토큰이 만료되었습니다"),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED , 401 , "해당 토큰은 유효한 토큰이 아닙니다"),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED , 401 , "AuthorizationHeader가 빈 값입니다"),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED , 401 , "인증 타입이 Bearer 타입이 아닙니다"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED,401, "해당 refresh token은 존재하지 않습니다" ),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,401 ,"해당 refresh token은 만료되었습니다"),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED,401 , "해당 토큰은 ACCESS TOKEN이 아닙니다"),
    INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST , 400 , "잘못된 회원 타입입니다."),
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST , 400 , "이미 가입한 회원입니다."),
    MEMBER_NOT_EXISTS(HttpStatus.BAD_REQUEST ,400,"해당 회원은 존재하지 않습니다" );

    private HttpStatus httpStatus;
    private Integer code;
    private String message;
}