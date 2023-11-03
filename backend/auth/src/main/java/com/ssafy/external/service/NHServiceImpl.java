package com.ssafy.external.service;

import com.ssafy.external.client.NHClient;
import com.ssafy.external.dto.NHDto;
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
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
    private final NHClient nhClient;
    @Value("${nh.client.id}")
    String id;
    @Value("${nh.client.secret}")
    String secret;

    @Override
    public NHDto.Response getKey() {
        NHDto.Response response = nhClient.getKey(
                CONTENT_TYPE,
                id,
                secret,
                "fintechapp",
                "client_credentials"
        );
        System.out.println(response);
        return response;
    }
}
