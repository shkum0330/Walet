package com.ssafy.member.db;

import com.ssafy.global.config.ClientConfig;
import com.ssafy.member.api.MemberDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@org.springframework.cloud.openfeign.FeignClient(name = "account", url="http://localhost:8082" , configuration = ClientConfig.class)
public interface FeignClient {
    @PostMapping("/admin/list/all-account")
    MemberDto.accountResponse getExternalData(@RequestBody MemberDto.transactionRequest memberIdsRequest);




    @GetMapping("/admin/count/all-account")
    MemberDto.TransactionResponse getTransactionData();

}
