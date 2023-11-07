package com.ssafy.service.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CheckDepositorFinAccountDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        String FinAcno;
        String BrdtBrno;
    }

    @Setter
    @Getter
    public static class Response{
        String Dpnm;
    }
}
