package com.ssafy.external.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NHResponseDto {
    String expireDay;
    String grant_type;
    String scope;
    String expireDate;
    String accessToken;
    String token_type;
}
