package com.ssafy.member.db;

import com.ssafy.global.config.ClientConfig;
import com.ssafy.member.api.MemberDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@org.springframework.cloud.openfeign.FeignClient(name = "account", url="${feign.url}/api/account", configuration = ClientConfig.class)
public interface FeignClient {
    @PostMapping("/admin/list/all-account")
    MemberDto.accountResponse getExternalData(@RequestBody MemberDto.transactionRequest memberIdsRequest, @RequestHeader("Authorization") String accessToken);

    @GetMapping("/admin/count/all-account")
    MemberDto.TransactionResponse getTransactionData();

}
