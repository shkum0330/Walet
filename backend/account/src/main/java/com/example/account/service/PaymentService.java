package com.example.account.service;

import com.example.account.api.request.payment.PaymentRequest;

public interface PaymentService {
    void requestPayment(PaymentRequest paymentRequest);
}
