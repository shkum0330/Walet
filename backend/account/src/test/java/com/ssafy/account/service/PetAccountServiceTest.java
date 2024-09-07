package com.ssafy.account.service;

import com.ssafy.account.api.request.account.AccountSaveRequest;
import com.ssafy.account.api.request.account.PetAccountSaveRequest;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.ssafy.account.common.domain.util.PasswordEncoder.*;
import static com.ssafy.account.db.entity.account.Account.AccountType.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles = "dev")
@Transactional
class PetAccountServiceTest {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void 일반계좌_생성() throws Exception {
        //given
        AccountSaveRequest request=new AccountSaveRequest("적금계좌","12345667","1234", NORMAL,null,null);
        //when
        Account account =accountService.registerGeneralAccount(1L,request);
        //then
        assertThat(account.getDepositorName()).isEqualTo("김민지");
        assertThat(checkPass("1234", account.getAccountPassword())).isTrue();
    }

    @Test
    public void 펫계좌_생성() throws Exception {
        //given
        PetAccountSaveRequest request=new PetAccountSaveRequest("펫계좌",
                "1234",null,"꼬맹이",
                "남아", LocalDate.of(2012,2,10),"말티즈",
                true,5.03f,null,"rfid1234",new ArrayList<>());
        //when
        Account account =accountService.registerPetAccount(1L,request);
        //then
        assertThat(account.getDepositorName()).isEqualTo("김민지");
        assertThat(account.getPetName()).isEqualTo("꼬맹이");
        assertThat(checkPass("1234", account.getAccountPassword())).isTrue();
    }


}