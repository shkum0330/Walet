package com.ssafy.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class FinAccountDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        @JsonProperty("FinAcno")
        String FinAcno; // 핀-어카운트
        @JsonProperty("BrdtBrno")
        String BrdtBrno; // 생년월일
        @JsonProperty("Tlno")
        String Tlno; // 전화번호
    }

    @Setter
    @Getter
    public static class Response{
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("FinAcnoStts")
        String FinAcnoStts;
        @JsonProperty("RgsnYmd")
        String RgsnYmd;
        @JsonProperty("Trymd")
        String Trymd;
    }
}
