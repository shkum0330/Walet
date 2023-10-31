package com.example.account.api.request.payment;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long sellerId;
    private Long paymentAmount;
}
