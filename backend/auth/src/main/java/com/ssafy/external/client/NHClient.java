package com.ssafy.external.client;

import com.ssafy.external.dto.NHDto;
import com.ssafy.global.config.ClientProxyConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(url = "http://smartdev.nonghyup.com:9460/svcapi" , name = "Server1" , configuration = ClientProxyConfig.class)
public interface NHClient {
    @PostMapping(value = "/oauth/requestToken.nhd" , consumes = "application/x-www-form-urlencoded")
    NHDto.Response getKey(@RequestParam("client_id") String clientId,
                         @RequestParam("client_secret") String clientSecret,
                         @RequestParam("scope") String scope,
                         @RequestParam("grant_type") String grantType);
}
