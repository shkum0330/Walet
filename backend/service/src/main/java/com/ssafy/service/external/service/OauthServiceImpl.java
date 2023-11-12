package com.ssafy.service.external.service;

import com.ssafy.service.external.client.OauthClient;
import com.ssafy.service.external.dto.NHDto;
import com.ssafy.service.util.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OauthServiceImpl implements OauthService{
    private final OauthClient oauthClient;
    private final RedisService redisService;
    @Value("${nh.client.id}")
    private String id;
    @Value("${nh.client.secret}")
    private String secret;
    private final String scope = "fintechapp";
    private final String grantType = "client_credentials";

    @Override
    public String getOauthKey() {
        String key = redisService.getKey();
        if(key != null){
            return key;
        }
        NHDto.Response response = oauthClient.getKey(
                id,
                secret,
                scope,
                grantType
        );
        redisService.saveKey(response.getAccessToken());
        return response.getAccessToken();
    }
}
