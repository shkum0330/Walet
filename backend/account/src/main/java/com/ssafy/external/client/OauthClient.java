package com.ssafy.external.client;

import com.ssafy.account.config.ClientConfig;
import com.ssafy.external.dto.OauthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://43.201.195.182" , name = "OauthClient" , configuration = ClientConfig.class)
public interface OauthClient {
    @GetMapping(value = "/api/auth/key")
    OauthDto.Response getKey();

    @GetMapping(value = "/api/auth/name")
    OauthDto.Response getUserName(@RequestParam("memberId") Long memberId);

    @GetMapping(value = "/api/auth/phone-number")
    OauthDto.Response getUserPhoneNumber(@RequestParam("memberId") Long memberId);
}
