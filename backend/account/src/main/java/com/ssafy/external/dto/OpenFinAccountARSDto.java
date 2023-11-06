package com.ssafy.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class OpenFinAccountARSDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        String Csnm;
        String BrdtBrno;
        String Tlno;
        String Acno;
        String DrtrRgyn;
    }

    @Getter
    @Setter
    public static class Response{
        String Rgno;
    }
}
