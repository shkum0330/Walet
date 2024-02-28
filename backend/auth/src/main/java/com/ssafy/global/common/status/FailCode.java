package com.ssafy.global.common.status;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum FailCode {

    GENERAL_ERROR(BAD_REQUEST, "데이터 처리 실패"),

    // 인증
    DIFFERENT_PASSWORD(FORBIDDEN, "비밀번호가 틀렸습니다."),
    DELETED_USER(BAD_REQUEST, "회원 탈퇴된 계정입니다."),
    UNSIGNED_USER(BAD_REQUEST, "가입되지 않은 아이디입니다."),
    BAD_TOKEN(BAD_REQUEST, "사용할 수 없는 토큰입니다."),
    UNMATCHED_TOKEN(BAD_REQUEST, "토큰에 일치하는 회원이 없습니다."),
    DIFFERENT_PIN(BAD_REQUEST, "핀 번호가 일치하지 않습니다."),

    // 검증
    EMAIL_EXIST(BAD_REQUEST, "중복된 이메읿입니다."),
    FIND_IMPOSSIBLE(BAD_REQUEST, "해당 회원을 찾을 수 없습니다."),
    UNMATCHED_CODE(BAD_REQUEST, "인증 번호가 일치하지 않습니다."),


    // 관리자
    STAFF_ONLY(FORBIDDEN, "관리자만 이용 가능한 서비스입니다.");

    private final HttpStatus status;
    private final String message;
}
