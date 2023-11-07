package com.ssafy.service.external.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.service.api.dto.FinAccountServiceDto;
import com.ssafy.service.external.client.NHFintechClient;
import com.ssafy.service.external.dto.*;
import com.ssafy.service.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public FinAccountServiceDto.Response OpenFinAccountARS(FinAccountServiceDto.Request data) {
        HeaderDto header = getHeader("OpenFinAccountARS");
        OpenFinAccountARSDto.Request request = OpenFinAccountARSDto.Request.builder()
                .Header(header)
                .Csnm(data.getCsnm())
                .BrdtBrno(data.getBrdtBrno())
                .Tlno(data.getTlno())
                .Acno(data.getAcno())
                .DrtrRgyn(data.getDrtrRgyn())
                .build();
        try {
//          핀어카운트 발급
            String key = "Basic " + oauthService.getOauthKey();
            String OpenFinAccountARSJsonString = nhFintechClient.OpenFinAccountARS(key , request);
            OpenFinAccountARSDto.Response OpenFinAccountARSResponse = objectMapper.readValue(OpenFinAccountARSJsonString , OpenFinAccountARSDto.Response.class);
            String CheckOpenFinAccountJsonString = CheckOpenFinAccount(OpenFinAccountARSResponse.getRgno() , data.getBrdtBrno() , data.getTlno() , key);

            CheckOpenFinAccountDto.Response CheckOpenFinAccountResponse = objectMapper.readValue(CheckOpenFinAccountJsonString , CheckOpenFinAccountDto.Response.class);


            while(CheckOpenFinAccountResponse.getHeader().getRpcd() != null && CheckOpenFinAccountResponse.getHeader().getRpcd().equals("A0017")){
                Thread.sleep(1000);
                CheckOpenFinAccountJsonString = CheckOpenFinAccount(OpenFinAccountARSResponse.getRgno() , data.getBrdtBrno() , data.getTlno() , key);
                CheckOpenFinAccountResponse = objectMapper.readValue(CheckOpenFinAccountJsonString , CheckOpenFinAccountDto.Response.class);
            }

            FinAccountServiceDto.Response response = FinAccountServiceDto.Response.builder()
                    .FinAcno(CheckOpenFinAccountResponse.getFinAcno())
                    .build();
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String CheckOpenFinAccount(String Rgno, String BrdtBrno, String Tlno , String key) {
        HeaderDto header = getHeader("CheckOpenFinAccount");
        CheckOpenFinAccountDto.Request request = CheckOpenFinAccountDto.Request.builder()
                .Header(header)
                .Rgno(Rgno)
                .BrdtBrno(BrdtBrno)
                .Tlno(Tlno)
                .build();
        return nhFintechClient.CheckOpenFinAccount(key, request);
    }

    @Override
    public void CloseFinAccount(String FinAcno, String Tlno, String BrdtBrno) {
        FinAccountDto.Request request = FinAccountDto.Request.builder()
                .FinAcno(FinAcno)
                .Tlno(Tlno)
                .BrdtBrno(BrdtBrno)
                .build();
        nhFintechClient.CloseFinAccount(request);
    }

    @Override
    public String InquireFinAccountStatus(String FinAcno, String Tlno, String BrdtBrno) {
        FinAccountDto.Request request = FinAccountDto.Request.builder()
                .FinAcno(FinAcno)
                .Tlno(Tlno)
                .BrdtBrno(BrdtBrno)
                .build();
        return nhFintechClient.InquireFinAccountStatus(request).getFinAcnoStts();
    }

    @Override
    public void DrawingTransfer2(String FinAcno, String Tram, String Vran, String DractOtlt, String MractOtlt) {
        DrawingTransfer2Dto.Request request = DrawingTransfer2Dto.Request.builder()
                .FinAcno(FinAcno)
                .Tram(Tram)
                .Vran(Vran)
                .DractOtlt(DractOtlt)
                .MractOtlt(MractOtlt)
                .build();
        nhFintechClient.DrawingTransfer2(request);
    }

    @Override
    public String CheckOnDrawingTransfer2(String FinAcno, String Tram, String Vran, String OrtrYmd, String OrtrIsTuno) {
        CheckOnDrawingTransfer2Dto.Request request = CheckOnDrawingTransfer2Dto.Request.builder()
                .FinAcno(FinAcno)
                .Tram(Tram)
                .Vran(Vran)
                .OrltYmd(OrtrYmd)
                .OrtrIsTuno(OrtrIsTuno)
                .build();
        return nhFintechClient.CheckOnDrawingTransfer2(request).getPcrs();
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
