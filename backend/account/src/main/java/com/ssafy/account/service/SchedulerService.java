package com.ssafy.account.service;

import com.ssafy.account.db.entity.transfer.Transfer;
import com.ssafy.account.db.repository.PaymentRepository;
import com.ssafy.account.db.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.account.db.entity.transfer.Transfer.TransferStatus.*;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final PaymentRepository paymentRepository;
    private final TransferRepository transferRepository;

    @Transactional
    @Async
    @Scheduled(cron = "0 0/1 * * * *", zone = "Asia/Seoul")
    public void autoDelete() { // 30분 지나면 삭제
        transferRepository.deleteByStatusAndCreatedAtLessThanEqual(PENDING,LocalDateTime.now().minusMinutes(30));
    }
}
