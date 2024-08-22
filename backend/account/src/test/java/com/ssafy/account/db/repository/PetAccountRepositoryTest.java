package com.ssafy.account.db.repository;

import com.ssafy.account.db.entity.account.PetAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PetAccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void findByMemberIdAndDepositorName() {
        PetAccount result=accountRepository.findByDepositorNameAndAccountNumber("관리자","301-2235-1775-31").get();
        assertThat(result.getId()).isEqualTo(5L);
    }

    @Test
    void findAccountByMemberIdAndAccountTypeAndAccountState(){
        List<PetAccount> result=accountRepository.findAccountByMemberIdAndAccountTypeAndAccountState(3L,"00","00");
        assertThat(result.size()).isEqualTo(2);
    }
}