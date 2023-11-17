package com.ssafy.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class InquireBalanceDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        @JsonProperty("FinAcno")
        String FinAcno; // 핀-어카운트
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
        @JsonProperty("FinAcno")
        String FinAcno; // 핀-어카운트
        @JsonProperty("Ldbl")
        String Ldbl; // 원장잔액
        @JsonProperty("RlpmAbamt")
        String RlpmAbamt; // 실지급가능액
    }
}
