package com.ssafy.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CheckOnDrawingTransfer2Dto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        @JsonProperty("FinAcno")
        String FinAcno;
        @JsonProperty("Tram")
        String Tram;
        @JsonProperty("Vran")
        String Vran;
        @JsonProperty("OrltYmd")
        String OrltYmd;
        @JsonProperty("OrtrIsTuno")
        String OrtrIsTuno;
    }

    @Setter
    @Getter
    public static class Response{
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("Pcrs")
        String Pcrs;
        @JsonProperty("Prtm")
        String Prtm;
    }
}
