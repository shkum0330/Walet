package com.ssafy.service.external.client;

import com.ssafy.service.config.ClientConfig;
import com.ssafy.service.external.dto.OauthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "http://43.201.195.182" , name = "OauthClient" , configuration = ClientConfig.class)
public interface OauthClient {
    @GetMapping(value = "/api/auth/key")
    OauthDto.Response getKey();
}
