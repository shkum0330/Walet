package com.ssafy.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CheckOpenFinAccountDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        @JsonProperty("Rgno")
        String Rgno;
        @JsonProperty("BrdtBrno")
        String BrdtBrno;
        @JsonProperty("Tlno")
        String Tlno;
    }

    @Getter
    @Setter
    public static class Response{
        @JsonProperty("Header")
        String Header;
        @JsonProperty("FinAcno")
        String FinAcno;
        @JsonProperty("RgsnYmd")
        String RgsnYmd;
        @JsonProperty("Acno")
        String Acno;
    }
}
