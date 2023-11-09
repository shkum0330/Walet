package com.ssafy.service.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

public class P2PNInvestmentManagementVirtualAccountListDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    public static class Request{
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("P2pVractUsg")
        String P2pVractUsg;
        @JsonProperty("Vran")
        String Vran;
        @JsonProperty("Lnsq")
        String Lnsq;
        @JsonProperty("PageNo")
        String PageNo;
    }

    @Getter
    @Setter
    public static class Response{
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("P2pVractUsg")
        String P2pVractUsg;
        @JsonProperty("TotCnt")
        String TotCnt;
        @JsonProperty("Iqtcnt")
        String Iqtcnt;
        @JsonProperty("PageNo")
        String PageNo;
        @JsonProperty("REC")
        List<REC> Rec;

        @Getter
        @Setter
        public static class REC{
            @JsonProperty("Vran")
            String Vran;
            @JsonProperty("InvstBrwNm")
            String InvstBrwNm;
            @JsonProperty("DwmBncd")
            String DwmBncd;
            @JsonProperty("DwmAcno")
            String DwmAcno;
            @JsonProperty("IsncYmd ")
            String IsncYmd ;
            @JsonProperty("VractStts")
            String VractStts;
            @JsonProperty("InvstDepsBal")
            String InvstDepsBal;
            @JsonProperty("RtnAblAmt")
            String RtnAblAmt;
            @JsonProperty("RpayDepsBal")
            String RpayDepsBal;
            @JsonProperty("Mnamt")
            String Mnamt;
            @JsonProperty("MinAmt")
            String MinAmt;
            @JsonProperty("RpayTrnsfYn")
            String RpayTrnsfYn;
        }
    }
}
