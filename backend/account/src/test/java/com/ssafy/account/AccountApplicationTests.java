package com.ssafy.account;

import com.ssafy.account.api.response.account.AdminAccountCountResponse;
import com.ssafy.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AccountApplicationTests {

	@Autowired
	AccountService accountService;

	@Test
	void getAllAccountCnt() {
		AdminAccountCountResponse adminAccountCountResponse = accountService.countAllAccountForAdmin();
		System.out.println("일반계좌 총 개수: " + adminAccountCountResponse.getGeneralAccountCount());
		System.out.println("펫계좌 총 개수: " + adminAccountCountResponse.getPetAccountCount());
	}

}
