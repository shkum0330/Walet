package com.example.account.service.impl;

import com.example.account.api.request.payment.PaymentRequest;
import com.example.account.db.entity.payment.Payment;
import com.example.account.db.repository.PaymentRepository;
import com.example.account.service.PaymentService;
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
