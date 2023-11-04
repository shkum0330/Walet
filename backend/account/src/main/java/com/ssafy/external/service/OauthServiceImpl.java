package com.ssafy.external.service;

import com.ssafy.external.client.OauthClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OauthServiceImpl implements OauthService{
    private final OauthClient oauthClient;

    @Override
    public String getOauthKey() {
        return oauthClient.getKey().getData();
    }
}
