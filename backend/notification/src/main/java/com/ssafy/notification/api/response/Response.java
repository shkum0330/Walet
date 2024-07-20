package com.ssafy.notification.api.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Map;

@Builder(toBuilder = true)
@Getter
public class Response {
    private String message;
    @Singular("response")
    private Map<String,Object> data;
}
