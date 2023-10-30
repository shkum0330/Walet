package com.example.account.common.api.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum FailCode {

    GENERAL_ERROR(BAD_REQUEST, "데이터 처리 실패"),

    // 계좌
    NO_ACCOUNT(NO_CONTENT, "입력하신 계좌는 존재하지 않습니다."),
    NO_COMPANY_ACCOUNT(NO_CONTENT, "해당 회사의 계좌가 존재하지 않습니다."),
    NO_TRANSFER_ACCOUNT(NO_CONTENT, "양도할 사람의 계좌가 존재하지 않습니다."),
    NO_TRANSFEREE_ACCOUNT(NO_CONTENT, "양도받을 사람의 계좌가 존재하지 않습니다."),
    NOT_USABLE_ACCOUNT(BAD_REQUEST, "현재 이 계좌는 사용이 불가합니다."),
    NOT_USABLE_COMPANY_ACCOUNT(BAD_REQUEST, "현재 해당 기업의 계좌는 사용이 불가합니다."),
    DUPLICATED_LINKED_ACCOUNT(BAD_REQUEST, "이미 등록돼있는 충전계좌입니다."),
    
    // 결제
    REJECT_ACCOUNT_PAYMENT(FORBIDDEN,"잔액 부족으로 결제에 실패했습니다.");

    private final HttpStatus status;
    private final String message;
}
