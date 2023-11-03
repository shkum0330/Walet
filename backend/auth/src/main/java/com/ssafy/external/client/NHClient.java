package com.ssafy.external.client;

import com.ssafy.external.dto.NHDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


//@FeignClient(url = "https://nhopenapi.nonghyup.com/svcapi" , name = "NHClient")
@FeignClient(url = "http://121.162.108.65:9460/svcapi" , name = "NHClient")
public interface NHClient {
    @PostMapping(value = "/oauth/requestToken.nhd" , consumes = "application/x-www-form-urlencoded; charset=UTF-8")
    NHDto.Response getKey(@PathVariable("client_id") String clientId,
                         @PathVariable("client_secret") String clientSecret,
                         @PathVariable("scope") String scope,
                         @PathVariable("grant_type") String grantType);
//    @PostMapping(value = "/oauth/requestToken.nhd" , consumes = "application/x-www-form-urlencoded; charset=UTF-8")
//    NHDto.Response getKey(@RequestBody NHDto.Request request);
}
