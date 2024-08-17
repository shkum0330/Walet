package com.ssafy.account.api.response.transaction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ssafy.account.db.entity.transaction.Transaction;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TransactionResponse {

    private Long id;
    private String counterPart;

    private String transactionType; // 거래 타입
    private Long paymentAmount; // 거래 금액
    private Long balance; // 거래 후 잔액
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime transactionDate; // 거래일자

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.transactionType = transaction.getTransactionType().getName();
        this.paymentAmount = transaction.getPaymentAmount();
        this.balance = transaction.getBalance();
        this.transactionDate = transaction.getTransactionTime();
    }
}
