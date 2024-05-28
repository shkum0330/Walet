package com.ssafy.account.service.impl;

import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.repository.AccountRepository;
import com.ssafy.account.service.TransactionService;
import com.ssafy.fixture.AccountFixture;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConcurrentTransferServiceTest {
    @Autowired
    TransactionService transactionService;
    @Autowired
    private AccountRepository accountRepository;
    Account senderAccount,receiverAccount;

    @BeforeEach
    public void setup() {
        senderAccount = AccountFixture.PET.getAccount();
        receiverAccount=AccountFixture.BUSINESS.getAccount();
    }


}