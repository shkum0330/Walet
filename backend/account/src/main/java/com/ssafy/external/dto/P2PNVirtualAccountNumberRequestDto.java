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
        String P2pVractUsg; // P2P 가상계좌 구분 -> 1로 고정
        @JsonProperty("InvstBrwNm")
        String InvstBrwNm; // 투자자및차입자명
        @JsonProperty("DwmBncd")
        String DwmBncd; // 입출금은행코드
        @JsonProperty("DwmAcno")
        String DwmAcno; // 입출금계좌번호
        @JsonProperty("Mnamt")
        String Mnamt; // 차입자가상계좌 입금가능금액(금액확정형)
        @JsonProperty("MinAmt")
        String MinAmt; // 최소입금금액(자우형)
        @JsonProperty("RpayTrnsfYn")
        String RpayTrnsfYn; // 상환자금처리용 여부

    }

    @Getter
    @Setter
    public static class Response{
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("P2pVractUsg")
        String P2pVractUsg; // P2P 가상계좌 구분
        @JsonProperty("Vran")
        String Vran; // 가상계좌번호
        @JsonProperty("InvstBrwNm")
        String InvstBrwNm; // 투자자 및 차입자명
        @JsonProperty("DwmBncd")
        String DwmBncd; // 입출금 은행코드
        @JsonProperty("DwmAcno")
        String DwmAcno; // 입출금 계좌번호
        @JsonProperty("Mnamt")
        String Mnamt; // 차입자가상계좌 입금가능금액
        @JsonProperty("MinAmt")
        String MinAmt; // 최소입금금액
    }
}
