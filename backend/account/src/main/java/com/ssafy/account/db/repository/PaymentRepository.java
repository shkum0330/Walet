package com.ssafy.account.db.repository;

import com.ssafy.account.common.api.status.ProcessStatus;
import com.ssafy.account.db.entity.payment.Payment;
import com.ssafy.account.db.entity.transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    void deleteByStatusAndCreatedAtLessThanEqual(ProcessStatus status, LocalDateTime createdAt);
}
