package com.ssafy.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
        @JsonProperty("Csnm")
        String Csnm; // 고객명
        @JsonProperty("BrdtBrno")
        String BrdtBrno; // 생년월일
        @JsonProperty("Tlno")
        String Tlno; // 전화번호
        @JsonProperty("Acno")
        String Acno; // 계좌번호
        @JsonProperty("DrtrRgyn")
        String DrtrRgyn; // 출금이체등록 여부
    }

    @Getter
    @Setter
    public static class Response{
        @JsonProperty("Header")
        String Header;
        @JsonProperty("Rgno")
        String Rgno; // 등록번호
    }
}
