package com.ssafy.external.service;

import com.ssafy.external.client.NHClient;
import com.ssafy.external.dto.NHDto;
import com.ssafy.global.common.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NHServiceImpl implements NHService{
    private final RedisService redisService;
    private final NHClient nhClient;
//    @Value("${nh.client.id}")
    private String id;
//    @Value("${nh.client.secret}")
    private String secret;
    private final String scope = "fintechapp";
    private final String grantType = "client_credentials";

    @Override
    public String getKey() {
        String key = redisService.getKey();
        if(key != null){
            return key;
        }
        NHDto.Response response = nhClient.getKey(
                id,
                secret,
                scope,
                grantType
        );
        redisService.saveKey(response.getAccessToken());
        return response.getAccessToken();
    }
}
