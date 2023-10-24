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
}
