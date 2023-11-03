package com.ssafy.account.common.api.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum FailCode {

    GENERAL_ERROR(BAD_REQUEST, "데이터 처리 실패"),

    // 인증
    DIFFERENT_PASSWORD(FORBIDDEN, "입력하신 비밀번호가 잘못되었습니다"),
    DIFFERENT_RFID(FORBIDDEN, "입력된 RFID코드가 잘못되었습니다."),

    // 계좌
    NO_ACCOUNT(NO_CONTENT, "입력하신 계좌는 존재하지 않습니다."),
    NO_COMPANY_ACCOUNT(NO_CONTENT, "해당 회사의 계좌가 존재하지 않습니다."),
    NO_RECEIVER_ACCOUNT(NO_CONTENT, "입력하신 계좌는 유효하지 않습니다. 올바른 계좌 번호를 다시 확인해 주세요."),
    NO_TRANSFER_ACCOUNT(NO_CONTENT, "양도할 사람의 계좌가 존재하지 않습니다."),
    NO_TRANSFEREE_ACCOUNT(NO_CONTENT, "양도받을 사람의 계좌가 존재하지 않습니다."),
    NOT_USABLE_ACCOUNT(BAD_REQUEST, "현재 이 계좌는 사용이 불가합니다."),
    NOT_USABLE_COMPANY_ACCOUNT(BAD_REQUEST, "현재 해당 기업의 계좌는 사용이 불가합니다."),
    NOT_USABLE_RECEIVER_ACCOUNT(BAD_REQUEST, "입력된 계좌는 현재 사용이 불가능한 상태입니다."),
    DUPLICATED_LINKED_ACCOUNT(BAD_REQUEST, "이미 등록돼있는 충전계좌입니다."),
    NO_NORMAL_ACCOUNT(NO_CONTENT, "현재 고객님의 일반계좌가 존재하지 않습니다."),
    NO_BUSINESS_ACCOUNT(NO_CONTENT, "현재 고객님의 사업자계좌가 존재하지 않습니다."),
    NO_PET_ACCOUNT(NO_CONTENT, "현재 고객님의 반려동물계좌가 존재하지 않습니다."),

    // 거래내역 접근 요청
    NOT_EXIST_ACCESS_REQUEST(NO_CONTENT, "존재하지 않거나 유효하지 않은 요청입니다."),
    EMPTY_ACCESS_REQUEST(NO_CONTENT, "요청내역이 존재하지 않습니다"),
    ALREADY_EXISTED_ACCESS(BAD_REQUEST, "이미 허용되었거나 현재 요청 중입니다."),

    // 거래내역 접근 가능 여부
    NO_PERMISSION_TO_TRANSACTION(NO_CONTENT, "해당 계좌의 거래내역에 접근할 수 없습니다."),

    // 결제
    REJECT_ACCOUNT_REMITTANCE(FORBIDDEN,"잔액 부족으로 송금 실패했습니다."),
    REJECT_ACCOUNT_PAYMENT(FORBIDDEN,"잔액 부족으로 결제에 실패했습니다."),

    //파일
    S3_DELETE_FAILURE(NO_CONTENT,"삭제할 파일이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
