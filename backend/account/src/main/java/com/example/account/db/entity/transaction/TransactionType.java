package com.example.account.db.entity.transaction;

import lombok.Getter;


public enum TransactionType {
    // 계좌거래종류
    DEPOSIT("입금"), WITHDRAWAL("출금");
    private String name;

    TransactionType(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
