package com.ssafy.external.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class NHDto {
    @Getter
    @Setter
    @ToString
    @Builder
    public static class Request{
        String client_id;
        String client_secret;
        String scope;
        String grant_type;
    }
    @Getter
    @Setter
    @ToString
    public static class Response{
        String expireDay;
        String grant_type;
        String scope;
        String expireDate;
        String accessToken;
        String token_type;;
    }
}
