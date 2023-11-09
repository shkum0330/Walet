package com.ssafy.service.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

public class P2PNVirtualAccountTerminateDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    public static class Request{
        @JsonProperty("Header")
        HeaderDto Header;
        @JsonProperty("Vran")
        String Vran;
    }

    @Getter
    @Setter
    public static class Response{
        @JsonProperty("Header")
        HeaderDto Header;
    }
}
