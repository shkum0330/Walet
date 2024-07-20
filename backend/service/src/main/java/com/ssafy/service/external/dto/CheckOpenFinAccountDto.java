package com.ssafy.service.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

public class CheckOpenFinAccountDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("Rgno")
        String Rgno;
        @JsonProperty("BrdtBrno")
        String BrdtBrno;
        @JsonProperty("Tlno")
        String Tlno;
    }

    @Getter
    @Setter
    @ToString
    public static class Response{
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("FinAcno")
        String FinAcno;
        @JsonProperty("RgsnYmd")
        String RgsnYmd;
        @JsonProperty("Acno")
        String Acno;
    }
}
