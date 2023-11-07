package com.ssafy.service.external.dto;

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
        String FinAcno;
        String Tram;
        String Vran;
        String DractOtlt;
        String MractOtlt;
    }
}
