package com.ssafy.account.service;

import com.ssafy.account.api.request.payment.PaymentRequest;
import com.ssafy.account.db.entity.payment.Payment;

public interface PaymentService {
    Long requestPayment(PaymentRequest paymentRequest);
    Payment findPayment(Long paymentId);

    void completePayment(Payment payment);

}
