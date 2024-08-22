package com.ssafy.account.service;

import com.ssafy.account.api.request.account.transfer.AccountTransferRequest;
import com.ssafy.account.db.entity.transfer.Transfer;
import com.ssafy.account.service.impl.TransferService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransferServiceTest {
    @Autowired
    TransferService transferService;

    @Test
    public void 조회_양수인id() throws Exception {
        //given
        Transfer transfer=transferService.findByTransfereeId(3L);
        //then
        Assertions.assertThat(transfer.getId()).isEqualTo(20);
    }


}