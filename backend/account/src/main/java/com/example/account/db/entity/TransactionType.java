package com.example.account.db.entity;

public enum TransactionType {
    // 계좌거래종류
    // 입금, 출금, 이체, 수신(다른 계좌에서 자금을 받음)
    DEPOSIT, WITHDRAWAL, TRANSFER, RECEIVE
}
