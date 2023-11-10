package com.ssafy.external.dto;

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
        @JsonProperty("FinAcno")
        String FinAcno; // 핀-어카운트
        @JsonProperty("Insymd")
        String Insymd; // 조회시작일자
        @JsonProperty("Ineymd")
        String Ineymd; // 조회종료일자
        @JsonProperty("TrnsDsnc")
        String TrnsDsnc; // 거래구분
        @JsonProperty("Lnsq")
        String Lnsq; // 정렬순서
        @JsonProperty("PageNo")
        String PageNo; // 페이지번호
        @JsonProperty("Dmcnt")
        String Dmcnt; // 요청건수
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
        String TotCnt; // 총건수
        @JsonProperty("REC")
        List<REC> REC;

        @Getter
        @Setter
        public static class REC{
            @JsonProperty("Trdd")
            String Trdd; // 거래일자
            @JsonProperty("Txtm")
            String Txtm; // 거래시각
            @JsonProperty("MnrcDrotDsnc")
            String MnrcDrotDsnc; // 입금출금구분
            @JsonProperty("Tram")
            String Tram; // 거래금액
            @JsonProperty("AftrBlnc")
            String AftrBlnc; // 거래후잔액
            @JsonProperty("TrnsAfAcntBlncSmblCd")
            String TrnsAfAcntBlncSmblCd; // 거래후계좌잔액부호코드
            @JsonProperty("Smr")
            String Smr; // 적요
            @JsonProperty("HnisCd")
            String HnisCd; // 취급기관코드
            @JsonProperty("HnbrCd")
            String HnbrCd; // 취급지점코드
            @JsonProperty("Ccyn")
            String Ccyn; // 취소여부
            @JsonProperty("Tuno")
            String Tuno; // 거래고유번호
            @JsonProperty("BnprCntn")
            String BnprCntn; // 통장인자내용
        }
    }
}
