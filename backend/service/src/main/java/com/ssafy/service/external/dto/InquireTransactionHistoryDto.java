package com.ssafy.service.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class InquireTransactionHistoryDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("FinAcno")
        String FinAcno;
        @JsonProperty("Insymd")
        String Insymd;
        @JsonProperty("Ineymd")
        String Ineymd;
        @JsonProperty("TrnsDsnc")
        String TrnsDsnc;
        @JsonProperty("Lnsq")
        String Lnsq;
        @JsonProperty("PageNo")
        String PageNo;
        @JsonProperty("Dmcnt")
        String Dmcnt;
    }

    @Setter
    @Getter
    public static class Response{
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("AthrCnfrTckt")
        String AthrCnfrTckt;
        @JsonProperty("WebUrl")
        String WebUrl;
        @JsonProperty("AndInltUrl")
        String AndInltUrl;
        @JsonProperty("AndAppUrl")
        String AndAppUrl;
        @JsonProperty("AndWebUrl")
        String AndWebUrl;
        @JsonProperty("IosInltUrl")
        String IosInltUrl;
        @JsonProperty("IosAppUrl")
        String IosAppUrl;
        @JsonProperty("IosWebUrl")
        String IosWebUrl;
        @JsonProperty("CtntDataYn")
        String CtntDataYn;
        @JsonProperty("TotCnt")
        String TotCnt;
        @JsonProperty("REC")
        List<REC> REC;

        @Getter
        @Setter
        public static class REC{
            @JsonProperty("Trdd")
            String Trdd;
            @JsonProperty("Txtm")
            String Txtm;
            @JsonProperty("MnrcDrotDsnc")
            String MnrcDrotDsnc;
            @JsonProperty("Tram")
            String Tram;
            @JsonProperty("AftrBlnc")
            String AftrBlnc;
            @JsonProperty("TrnsAfAcntBlncSmblCd")
            String TrnsAfAcntBlncSmblCd;
            @JsonProperty("Smr")
            String Smr;
            @JsonProperty("HnisCd")
            String HnisCd;
            @JsonProperty("HnbrCd")
            String HnbrCd;
            @JsonProperty("Ccyn")
            String Ccyn;
            @JsonProperty("Tuno")
            String Tuno;
            @JsonProperty("BnprCntn")
            String BnprCntn;

        }
    }
}
