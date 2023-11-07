package com.ssafy.account.api.response.transaction;

import com.ssafy.account.db.entity.transaction.Transaction;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberAccountTransactionResponse {
    // 계좌명, 거래대상, 거래금액, 거래타입, 거래일자
    private String accountName;
    private String counterpart;
    private Long paymentAmount;
    private String transactionType;
    private LocalDateTime transactionTime;

    public MemberAccountTransactionResponse(Transaction transaction, String counterpart) {
        this.accountName = transaction.getAccount().getAccountName();
        this.counterpart = counterpart;
        this.paymentAmount = transaction.getPaymentAmount();
        this.transactionType = transaction.getTransactionType().getName();
        this.transactionTime = transaction.getTransactionTime();
    }

}
