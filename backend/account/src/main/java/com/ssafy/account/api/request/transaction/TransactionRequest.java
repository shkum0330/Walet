package com.ssafy.account.api.request.transaction;

import com.ssafy.account.db.entity.transaction.TransactionType;
import lombok.Data;

@Data
public class TransactionRequest {
    private String rfidCode;
    private Long myAccountId; // 내 계좌번호
    private Long companyAccountId; // 이체할 회사의 계좌번호
    private TransactionType transactionType; // 거래 타입
    private Long paymentAmount; // 거래 금액
}
