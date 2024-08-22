package com.ssafy.account.service.impl;

import com.ssafy.account.api.request.transaction.RemittanceRequest;
import com.ssafy.account.db.entity.account.PetAccount;
import com.ssafy.account.db.repository.AccountRepository;
import com.ssafy.fixture.AccountFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ConcurrentTransferServiceTest {
    @Autowired
    TransactionService transactionService;
    @Autowired
    private AccountRepository accountRepository;
    PetAccount senderPetAccount, receiverPetAccount;

    @BeforeEach
    public void setup() {
        senderPetAccount = AccountFixture.PET.getAccount();
        receiverPetAccount =AccountFixture.BUSINESS.getAccount();
    }

    @Test
    @Transactional
    public void 동시_입금_테스트() throws InterruptedException {
        int numOfThreads = 10; // Number of concurrent threads
        long remittanceAmount = 100L; // Amount to remit in each transaction
        CountDownLatch latch = new CountDownLatch(numOfThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);

        for (int i = 0; i < numOfThreads; i++) {
            executor.execute(() -> {
                try {
                    // 각 쓰레드에 송금 요청을 보낸다.
                    RemittanceRequest request = new RemittanceRequest(senderPetAccount.getId(), receiverPetAccount.getId(),"1234",1000L);
                    transactionService.addRemittanceTransaction(request);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        // Wait for all threads to finish
        latch.await();

        PetAccount sender = accountRepository.findById(senderPetAccount.getId()).orElse(null);
        PetAccount receiver = accountRepository.findById(receiverPetAccount.getId()).orElse(null);

        assert sender != null;
        assertEquals(0L, sender.getBalance());
        assertEquals(numOfThreads * remittanceAmount, receiver.getBalance());

    }
}