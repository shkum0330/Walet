package com.ssafy.account.service;

import com.ssafy.account.db.entity.account.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {
    @Autowired
    AccountService accountService;

    @Test
    public void findByAccountNumber() throws Exception {
        //given
        Account account=accountService.findByAccountNumber("3010168334251");
        //when

        //then
        assertThat(account.getDepositorName()).isEqualTo("배수우록");
    }

}