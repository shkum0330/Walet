package com.ssafy.account.db.repository;

import com.ssafy.account.common.api.status.ProcessStatus;
import com.ssafy.account.db.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByBuyerId(Long buyerId);
    void deleteByBuyerId(Long buyerId);
    void deleteByStatusAndCreatedAtLessThanEqual(ProcessStatus status, LocalDateTime createdAt);

}
