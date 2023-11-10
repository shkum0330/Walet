package com.ssafy.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ReceivedTransferFinAccountDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        @JsonProperty("FinAcno")
        String FinAcno; // 핀-어카운트
        @JsonProperty("Tram")
        String Tram; // 거래금액
        @JsonProperty("DractOtlt")
        String DractOtlt; // 출금계좌인자내용
        @JsonProperty("MractOtlt")
        String MractOtlt; // 입금계좌인자내용
    }

    @Getter
    @Setter
    public static class Response{
        @JsonProperty("Header")
        HeaderDto Header;
    }
}
