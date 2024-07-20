package com.ssafy.account.db.entity.transaction;


public enum TransactionType {
    // 계좌거래종류
    DEPOSIT("입금"), WITHDRAWAL("출금"), TRANSFER("펫계좌 양도");
    private String name;

    TransactionType(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
