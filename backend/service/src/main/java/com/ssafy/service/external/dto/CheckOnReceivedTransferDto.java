package com.ssafy.service.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CheckOnReceivedTransferDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        String FinAcno;
        String Bncd;
        String Acno;
        String Tram;
        String OrtrYmd;
        String OrtrIsTuno;
    }

    @Setter
    @Getter
    public static class Response{
        String Pcrs;
        String Prtm;
    }
}
