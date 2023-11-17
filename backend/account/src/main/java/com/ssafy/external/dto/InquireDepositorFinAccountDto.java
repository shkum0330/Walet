package com.ssafy.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class InquireDepositorFinAccountDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        @JsonProperty("FinAcno")
        String FinAcno;
    }

    @Setter
    @Getter
    public static class Response{
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("FinAcno")
        String FinAcno;
        @JsonProperty("Dpnm")
        String Dpnm;
    }
}
