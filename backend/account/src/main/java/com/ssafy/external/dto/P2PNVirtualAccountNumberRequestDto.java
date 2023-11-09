package com.ssafy.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

public class P2PNVirtualAccountNumberRequestDto {
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
        @JsonProperty("InvstBrwNm")
        String InvstBrwNm;
        @JsonProperty("DwmBncd")
        String DwmBncd;
        @JsonProperty("DwmAcno")
        String DwmAcno;
        @JsonProperty("Mnamt")
        String Mnamt;
        @JsonProperty("MinAmt")
        String MinAmt;
        @JsonProperty("RpayTrnsfYn")
        String RpayTrnsfYn;

    }

    @Getter
    @Setter
    public static class Response{
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("P2pVractUsg")
        String P2pVractUsg;
        @JsonProperty("Vran")
        String Vran;
        @JsonProperty("InvstBrwNm")
        String InvstBrwNm;
        @JsonProperty("DwmBncd")
        String DwmBncd;
        @JsonProperty("DwmAcno")
        String DwmAcno;
        @JsonProperty("Mnamt")
        String Mnamt;
        @JsonProperty("MinAmt")
        String MinAmt;
    }
}
