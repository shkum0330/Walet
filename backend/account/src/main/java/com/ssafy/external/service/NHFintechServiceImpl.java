package com.ssafy.external.service;

import com.ssafy.external.client.NHFintechClient;
import com.ssafy.external.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NHFintechServiceImpl implements NHFintechService{
    private final NHFintechClient nhFintechClient;

    @Override
    public String OpenFinAccountARS(String Csnm, String BrdtBrno, String Tlno, String Acno, String DrtrRgyn) {
        OpenFinAccountARSDto.Request request = new OpenFinAccountARSDto.Request(Csnm,BrdtBrno,Tlno,Acno,DrtrRgyn);
        return nhFintechClient.OpenFinAccountARS(request).getRgno();
    }

    @Override
    public String CheckOpenFinAccount(String Rgno, String BrdtBrno, String Tlno) {
        CheckOpenFinAccountDto.Request request = new CheckOpenFinAccountDto.Request(Rgno, BrdtBrno, Tlno);
        return nhFintechClient.CheckOpenFinAccount(request).getFinAcno();
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
}
