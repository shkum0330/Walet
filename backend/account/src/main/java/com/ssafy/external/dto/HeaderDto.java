package com.ssafy.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HeaderDto {
    @JsonProperty("ApiNm")
    String ApiNm;
    @JsonProperty("Tsymd")
    String Tsymd;
    @JsonProperty("Trtm")
    String Trtm;
    @JsonProperty("Iscd")
    String Iscd;
    @JsonProperty("FintechApsno")
    String FintechApsno;
    @JsonProperty("ApiSvcCd")
    String ApiSvcCd;
    @JsonProperty("IsTuno")
    String IsTuno;
    @JsonProperty("LritCd")
    String LritCd;
    @JsonProperty("Rpcd")
    String Rpcd;
    @JsonProperty("Rsms")
    String Rsms;
}
