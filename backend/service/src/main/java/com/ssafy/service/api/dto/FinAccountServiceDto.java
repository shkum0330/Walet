package com.ssafy.service.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class FinAccountServiceDto {
    @Getter
    @Setter
    public static class Request{
        @JsonProperty("Csnm")
        String Csnm;
        @JsonProperty("BrdtBrno")
        String BrdtBrno;
        @JsonProperty("Tlno")
        String Tlno;
        @JsonProperty("Acno")
        String Acno;
        @JsonProperty("DrtrRgyn")
        String DrtrRgyn;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response{
        @JsonProperty("FinAcno")
        String FinAcno;
    }
}
