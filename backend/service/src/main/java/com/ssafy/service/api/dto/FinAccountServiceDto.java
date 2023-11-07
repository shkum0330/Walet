package com.ssafy.service.api.dto;

import lombok.Getter;
import lombok.Setter;

public class FinAccountServiceDto {
    @Getter
    @Setter
    public static class Request{
        String Csnm;
        String BrdtBrno;
        String Tlno;
        String Acno;
        String DrtrRgyn;
    }
}
