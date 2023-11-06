package com.ssafy.external.dto;

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
        String FinAcno;
        String Tram;
        String DractOtlt;
        String MractOtlt;
    }
}
