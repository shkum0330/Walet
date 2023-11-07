package com.ssafy.service.external.dto;

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
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("FinAcno")
        String FinAcno;
        @JsonProperty("BrdtBrno")
        String BrdtBrno;
        @JsonProperty("Tlno")
        String Tlno;
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
