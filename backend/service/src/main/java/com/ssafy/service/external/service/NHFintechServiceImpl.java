package com.ssafy.service.external.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.service.external.client.NHFintechClient;
import com.ssafy.service.external.dto.*;
import com.ssafy.service.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NHFintechServiceImpl implements NHFintechService{
    private final NHFintechClient nhFintechClient;
    private final OauthService oauthService;
    private final ObjectMapper objectMapper;
    private final TimeUtil timeUtil;

    @Override
    public OpenFinAccountARSDto.Response OpenFinAccountARS(OpenFinAccountARSDto.Request data) {
        HeaderDto header = getHeader("OpenFinAccountARS");
        OpenFinAccountARSDto.Request request = OpenFinAccountARSDto.Request.builder()
                .Header(header)
                .Csnm(data.getCsnm())
                .BrdtBrno(data.getBrdtBrno())
                .Tlno(data.getTlno())
                .Acno(data.getAcno())
                .DrtrRgyn(data.getDrtrRgyn())
                .build();
        String jsonString = nhFintechClient.OpenFinAccountARS("Basic " + oauthService.getOauthKey() , request);
        try {
            OpenFinAccountARSDto.Response response = objectMapper.readValue(jsonString , OpenFinAccountARSDto.Response.class);
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public CheckOpenFinAccountDto.Response CheckOpenFinAccount(CheckOpenFinAccountDto.Request data) {
        HeaderDto header = getHeader("CheckOpenFinAccount");
        CheckOpenFinAccountDto.Request request = CheckOpenFinAccountDto.Request.builder()
                .Header(header)
                .Rgno(data.getRgno())
                .BrdtBrno(data.getBrdtBrno())
                .Tlno(data.getTlno())
                .build();
        String jsonString = nhFintechClient.CheckOpenFinAccount("Basic " + oauthService.getOauthKey(), request);
        try {
            return objectMapper.readValue(jsonString , CheckOpenFinAccountDto.Response.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FinAccountDto.Response CloseFinAccount(FinAccountDto.Request data) {
        HeaderDto header = getHeader("CloseFinAccount");
        FinAccountDto.Request request = FinAccountDto.Request.builder()
                .Header(header)
                .FinAcno(data.getFinAcno())
                .Tlno(data.getTlno())
                .BrdtBrno(data.getBrdtBrno())
                .build();
        String jsonString = nhFintechClient.CloseFinAccount("Basic " + oauthService.getOauthKey(), request);
        try {
            return objectMapper.readValue(jsonString , FinAccountDto.Response.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FinAccountDto.Response InquireFinAccountStatus(FinAccountDto.Request data) {
        HeaderDto header = getHeader("InquireFinAccountStatus");
        FinAccountDto.Request request = FinAccountDto.Request.builder()
                .Header(header)
                .FinAcno(data.getFinAcno())
                .Tlno(data.getTlno())
                .BrdtBrno(data.getBrdtBrno())
                .build();
        String jsonString = nhFintechClient.InquireFinAccountStatus("Basic " + oauthService.getOauthKey() , request);
        try {
            return objectMapper.readValue(jsonString , FinAccountDto.Response.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DrawingTransfer2Dto.Response DrawingTransfer2(DrawingTransfer2Dto.Request data) {
        HeaderDto header = getHeader("DrawingTransfer2");
        DrawingTransfer2Dto.Request request = DrawingTransfer2Dto.Request.builder()
                .Header(header)
                .FinAcno(data.getFinAcno())
                .Tram(data.getTram())
                .Vran(data.getVran())
                .DractOtlt(data.getDractOtlt())
                .MractOtlt(data.getMractOtlt())
                .build();
        String jsonString = nhFintechClient.DrawingTransfer2("Basic " + oauthService.getOauthKey() , request);
        try {
            return objectMapper.readValue(jsonString , DrawingTransfer2Dto.Response.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CheckOnDrawingTransfer2Dto.Response CheckOnDrawingTransfer2(CheckOnDrawingTransfer2Dto.Request data) {
        HeaderDto header = getHeader("CheckOnDrawingTransfer2Dto");
        CheckOnDrawingTransfer2Dto.Request request = CheckOnDrawingTransfer2Dto.Request.builder()
                .FinAcno(data.getFinAcno())
                .Tram(data.getTram())
                .Vran(data.getVran())
                .OrltYmd(data.getOrltYmd())
                .OrtrIsTuno(data.getOrtrIsTuno())
                .build();
        String jsonString = nhFintechClient.CheckOnDrawingTransfer2("Basic " + oauthService.getOauthKey(),request);
        try {
            return objectMapper.readValue(jsonString , CheckOnDrawingTransfer2Dto.Response.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void ReceivedTransferFinAccount(String FinAcno, String Tram, String DractOtlt, String MractOtlt) {
        ReceivedTransferFinAccountDto.Request request = ReceivedTransferFinAccountDto.Request.builder()
                .FinAcno(FinAcno)
                .Tram(Tram)
                .DractOtlt(DractOtlt)
                .MractOtlt(MractOtlt)
                .build();
        nhFintechClient.ReceivedTransferFinAccount(request);
    }

    @Override
    public String CheckOnReceivedTransfer(String FinAcno, String Bncd, String Acno, String Tram, String OrtrYmd, String OrtrIsTuno) {
        CheckOnReceivedTransferDto.Request request = CheckOnReceivedTransferDto.Request.builder()
                .FinAcno(FinAcno)
                .Bncd(Bncd)
                .Acno(Acno)
                .Tram(Tram)
                .OrtrYmd(OrtrYmd)
                .OrtrIsTuno(OrtrIsTuno)
                .build();
        return nhFintechClient.CheckOnReceivedTransfer(request).getPcrs();
    }

    @Override
    public String CheckDepositorFinAccount(String FinAcno, String BrdtBrno) {
        CheckDepositorFinAccountDto.Request request = CheckDepositorFinAccountDto.Request.builder()
                .FinAcno(FinAcno)
                .BrdtBrno(BrdtBrno)
                .build();
        return nhFintechClient.CheckDepositorFinAccount(request).getDpnm();
    }

    @Override
    public String InquireDepositorFinAccount(String FinAcno) {
        InquireDepositorFinAccountDto.Request request = InquireDepositorFinAccountDto.Request.builder()
                .FinAcno(FinAcno)
                .build();
        return nhFintechClient.InquireDepositorFinAccount(request).getDpnm();
    }

    @Override
    public Long InquireBalance(String FinAcno) {
        InquireBalanceDto.Request request = InquireBalanceDto.Request.builder()
                .FinAcno(FinAcno)
                .build();
        return Long.parseLong(nhFintechClient.InquireBalance(request).getLdbl());
    }

    @Override
    public List<InquireTransactionHistoryDto.Response.REC> InquireTransactionHistory(String FinAcno, String Insymd, String Ineymd, String TrnsDsnc, String Lnsq, String PageNo, String Dmcnt) {
        InquireTransactionHistoryDto.Request request = InquireTransactionHistoryDto.Request.builder()
                .FinAcno(FinAcno)
                .Insymd(Insymd)
                .Ineymd(Ineymd)
                .TrnsDsnc(TrnsDsnc)
                .Lnsq(Lnsq)
                .PageNo(PageNo)
                .Dmcnt(Dmcnt)
                .build();
        return nhFintechClient.InquireTransactionHistory(request).getREC();
    }

    private byte[] encode(Object objects){
        try {
            return objectMapper.writeValueAsBytes(objects);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private HeaderDto getHeader(String ApiNm) {
        return HeaderDto.builder()
                .ApiNm(ApiNm)
                .Tsymd(timeUtil.YMD(LocalDateTime.now()))
                .Trtm(timeUtil.HMS(LocalDateTime.now()))
                .Iscd("000449")
                .FintechApsno("001")
                .ApiSvcCd("01E_024_00")
                .IsTuno(timeUtil.YMDHMS(LocalDateTime.now()))
                .build();
    }
}
