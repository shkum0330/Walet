package com.ssafy.external.client;

import com.ssafy.account.config.ClientConfig;
import com.ssafy.external.dto.OauthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "/api/service" , name = "OauthClient" , configuration = ClientConfig.class)
public interface OauthClient {
    @GetMapping(value = "/api/auth/name/{memberId}")
    OauthDto.Response getUserName(@PathVariable("memberId") Long memberId);

    @GetMapping(value = "/api/auth/phone-number/{memberId}")
    OauthDto.Response getUserPhoneNumber(@PathVariable("memberId") Long memberId);
}
