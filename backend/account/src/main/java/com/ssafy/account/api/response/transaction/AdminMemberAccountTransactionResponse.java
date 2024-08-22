package com.ssafy.account.api.response.transaction;

import com.ssafy.account.db.entity.transaction.Transaction;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class AdminMemberAccountTransactionResponse {
    // 계좌명, 거래대상, 거래금액, 거래타입, 거래일자
    private String accountName;
    private String counterpart;
    private Long paymentAmount;
    private String transactionType;
    private String transactionTime;

    public AdminMemberAccountTransactionResponse(Transaction transaction, String counterpart, String transactionType) {
        this.accountName = transaction.getPetAccount().getAccountName();
        this.counterpart = counterpart;
        this.paymentAmount = transaction.getPaymentAmount();
        this.transactionType = transactionType;
        if (transaction.getTransactionTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
            this.transactionTime = formatter.format(transaction.getTransactionTime());
        }
    }


}
