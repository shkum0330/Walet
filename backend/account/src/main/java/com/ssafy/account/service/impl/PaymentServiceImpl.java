package com.ssafy.account.service.impl;

import com.ssafy.account.api.request.payment.PaymentRequest;
import com.ssafy.account.common.api.exception.NotFoundException;
import com.ssafy.account.common.api.status.ProcessStatus;
import com.ssafy.account.db.entity.payment.Payment;
import com.ssafy.account.db.repository.PaymentRepository;
import com.ssafy.account.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.account.common.api.status.FailCode.NO_PAYMENT;
import static com.ssafy.account.common.api.status.ProcessStatus.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 반드시확인반드시확인반드시확인반드시확인반드시확인반드시확인반드시확인반드시확인반드시확인
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public Long requestPayment(PaymentRequest paymentRequest) {
        log.info("paymentRequest: {}",paymentRequest );
        return paymentRepository.save(new Payment(paymentRequest.getSellerId(),
                PENDING, paymentRequest.getPaymentAmount())).getId();
    }

    @Override
    public Payment findPayment(Long paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException(NO_PAYMENT));
    }

    @Override
    @Transactional
    public void completePayment(Payment payment) {
        payment.completePayment();
    }

}
