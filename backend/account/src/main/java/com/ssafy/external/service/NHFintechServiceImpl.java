package com.ssafy.external.service;

import com.ssafy.external.client.NHFintechClient;
import com.ssafy.external.dto.OpenFinAccountARSDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NHFintechServiceImpl implements NHFintechService{
    private final NHFintechClient nhFintechClient;

    @Override
    public String OpenFinAccountARS(String Csnm, String BrdtBrno, String Tlno, String Acno, String DrtrRgyn) {
        OpenFinAccountARSDto.Request request = new OpenFinAccountARSDto.Request(Csnm,BrdtBrno,Tlno,Acno,DrtrRgyn);
        return nhFintechClient.OpenFinAccountARS(request).getRGNO();
    }
}
