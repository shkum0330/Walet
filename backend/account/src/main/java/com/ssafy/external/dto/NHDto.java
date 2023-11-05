package com.ssafy.external.dto;

import lombok.Getter;
import lombok.Setter;

public class NHDto {
    @Getter
    @Setter
    public static class OpenFinAccountARSResponse{
        String RGNO;
    }

    @Getter
    @Setter
    public static class CheckOpenFinAccountResponse{
        String FinAcno;
        String RgsnYmd;
        String Acno;
    }


}
