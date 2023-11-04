package com.ssafy.external.client;

import com.ssafy.external.dto.OauthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "http://43.201.195.182" , name = "OauthClient")
public interface OauthClient {
    @GetMapping(value = "/api/auth/key")
    OauthDto.Response getKey();
}
