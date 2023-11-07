package com.ssafy.account.api.request.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
public class PaymentNotificationRequest implements Serializable {
    Long receiveMemberId;
    int price;
    String sender; // 결제처

    public PaymentNotificationRequest(Long receiveMemberId, int price, String sender) {
        this.receiveMemberId = receiveMemberId;
        this.price = price;
        this.sender = sender;
    }
}
