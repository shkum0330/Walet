package com.ssafy.account.db.repository;

import com.ssafy.account.db.entity.payment.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentRepositoryTest {

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void find() throws Exception {

    }

}