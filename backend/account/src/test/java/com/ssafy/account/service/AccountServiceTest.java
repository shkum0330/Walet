package com.ssafy.account.service;

import com.ssafy.account.api.request.account.AccountSaveRequest;
import com.ssafy.account.common.domain.util.PasswordEncoder;
import com.ssafy.account.db.entity.account.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.account.common.domain.util.PasswordEncoder.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles = "dev")
@Transactional
class AccountServiceTest {
    @Autowired
    AccountService accountService;
    @Test
    public void 일반계좌_생성() throws Exception {
        //given
        AccountSaveRequest request=new AccountSaveRequest("예금계좌","00",null,"1234",null);
        //when
        Account account=accountService.registerGeneralAccount(1L,request);
        //then
        assertThat(account.getDepositorName()).isEqualTo("김민지");
        assertThat(checkPass("1234",account.getAccountPassword())).isTrue();
    }

    @Test
    public void findByAccountNumber() throws Exception {
        //given
        Account account=accountService.findByAccountNumber("3010168334251");
        //when

        //then
        assertThat(account.getDepositorName()).isEqualTo("배수우록");
    }

}