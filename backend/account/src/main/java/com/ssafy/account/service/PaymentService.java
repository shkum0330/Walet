package com.ssafy.account.service;

import com.ssafy.account.api.request.payment.PaymentRequest;

public interface PaymentService {
    void requestPayment(PaymentRequest paymentRequest);
}
