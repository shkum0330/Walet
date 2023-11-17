package com.ssafy.service.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class DrawingTransfer2Dto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("FinAcno")
        String FinAcno;
        @JsonProperty("Tram")
        String Tram;
        @JsonProperty("Vran")
        String Vran;
        @JsonProperty("DractOtlt")
        String DractOtlt;
        @JsonProperty("MractOtlt")
        String MractOtlt;
    }
    @Getter
    @Setter
    public static class Response{
        @JsonProperty("Header")
        HeaderDto Header;
    }
}
