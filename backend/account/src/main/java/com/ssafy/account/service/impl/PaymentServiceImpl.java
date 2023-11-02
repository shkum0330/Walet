package com.ssafy.account.service.impl;

import com.ssafy.account.api.request.payment.PaymentRequest;
import com.ssafy.account.db.entity.payment.Payment;
import com.ssafy.account.db.repository.PaymentRepository;
import com.ssafy.account.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public void requestPayment(PaymentRequest paymentRequest) {
        log.info("paymentRequest: {}",paymentRequest );
        paymentRepository.save(new Payment(paymentRequest.getSellerId(),Payment.PaymentStatus.PENDING, paymentRequest.getPaymentAmount()));
    }
}
