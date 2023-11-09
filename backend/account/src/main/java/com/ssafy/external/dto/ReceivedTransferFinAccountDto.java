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
        String FinAcno;
        @JsonProperty("Tram")
        String Tram;
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
