package com.ssafy.service.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

public class OpenFinAccountARSDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    public static class Request{
        @JsonProperty("Header")
        HeaderDto Header;
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
        HeaderDto Header;
        String Rgno;
    }
}
