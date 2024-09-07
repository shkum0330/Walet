package com.ssafy.account.service;

import com.ssafy.account.api.request.access.AccessSaveRequest;
import com.ssafy.account.db.entity.access.Access;
import com.ssafy.account.db.entity.account.Account;
import com.ssafy.account.db.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles(profiles = "dev")
@Transactional
class AccessServiceTest {
    @Autowired
    AccessService accessService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void 접근권한_생성() throws Exception {
        //given
        Account account =accountRepository.findByMemberIdAndAccountType(1L,"02").get();
        AccessSaveRequest request=new AccessSaveRequest("꼬맹이","0035791444046","안녕하세요");
        //when
        Access access=accessService.createAccessRequest(2L,"강해린",request);
        //then
        Assertions.assertThat(access.getIsConfirmed()).isFalse();
        Assertions.assertThat(access.getPetName()).isEqualTo("꼬맹이");
    }
}