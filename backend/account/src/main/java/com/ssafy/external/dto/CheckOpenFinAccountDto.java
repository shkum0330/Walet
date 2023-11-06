package com.ssafy.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CheckOpenFinAccountDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        String Rgno;
        String BrdtBrno;
        String Tlno;
    }

    @Getter
    @Setter
    public static class Response{
        String FinAcno;
        String RgsnYmd;
        String Acno;
    }
}
