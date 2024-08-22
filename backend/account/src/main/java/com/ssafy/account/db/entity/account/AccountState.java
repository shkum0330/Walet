package com.ssafy.account.db.entity.account;

import lombok.Getter;

@Getter
public enum AccountState {
    ACTIVE("00"),
    LOCKED("01"),
    STOPPED("10"),
    CLOSED("11");

    private final String code;

    AccountState(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static AccountState getState(String code) {
        for (AccountState state : values()) {
            if (state.getCode().equals(code)) {
                return state;
            }
        }
        throw new IllegalArgumentException("잘못된 계좌 상태 코드: " + code);
    }
}
