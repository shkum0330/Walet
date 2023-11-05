package com.ssafy.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class FinAccountDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        String FinAcno;
        String BrdtBrno;
        String Tlno;
    }

    @Setter
    @Getter
    public static class Response{
        String FinAcnoStts;
        String RgsnYmd;
        String Trymd;
    }
}
