package com.ssafy.external.service;

import com.ssafy.external.client.NHClient;
import com.ssafy.external.dto.NHDto;
import com.ssafy.external.dto.TestDto;
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
    private final NHClient nhClient;
    @Value("${nh.client.id}")
    private String id;
    @Value("${nh.client.secret}")
    private String secret;
    private final String scope = "fintechapp";
    private final String grantType = "client_credentials";

    @Override
    public NHDto.Response getKey() {
        NHDto.Response response = nhClient.getKey(
                id,
                secret,
                scope,
                grantType
        );
        return response;

//        TestDto testDto = new TestDto();
//        testDto.setEmail("emasd.com");
//        testDto.setPassword("000000");
//        System.out.println(nhClient.getKey(testDto));
//        return null;
    }
}
