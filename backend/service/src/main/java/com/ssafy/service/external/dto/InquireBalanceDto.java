package com.ssafy.service.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class InquireBalanceDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        String FinAcno;
    }

    @Setter
    @Getter
    public static class Response{
        String AthrCnfrTckt;
        String WebUrl;
        String AndInltUrl;
        String AndAppUrl;
        String AndWebUrl;
        String IosInltUrl;
        String IosAppUrl;
        String IosWebUrl;
        String FinAcno;
        String Ldbl;
        String RlpmAbamt;
    }
}
