package com.ssafy.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class OpenFinAccountARSDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
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
    public static class Response{
        @JsonProperty("Header")
        String Header;
        @JsonProperty("Rgno")
        String Rgno;
    }
}
