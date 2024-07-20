package com.ssafy.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
        @JsonProperty("Rgno")
        String Rgno; // 등록번호
        @JsonProperty("BrdtBrno")
        String BrdtBrno; // 생년월일
        @JsonProperty("Tlno")
        String Tlno; // 전화번호
    }

    @Getter
    @Setter
    public static class Response{
        @JsonProperty("Header")
        String Header;
        @JsonProperty("FinAcno")
        String FinAcno; // 핀-어카운트
        @JsonProperty("RgsnYmd")
        String RgsnYmd; // 등록일자
        @JsonProperty("Acno")
        String Acno; // 계좌번호
    }
}
