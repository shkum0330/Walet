package com.ssafy.notification.api.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class PaymentNotificationRequest implements Serializable {
    Long receiveMemberId;
    String content;
    String sender; // 결제처

    public PaymentNotificationRequest(Long receiveMemberId, String content, String sender) {
        this.receiveMemberId = receiveMemberId;
        this.content = content;
        this.sender = sender;
    }
}
