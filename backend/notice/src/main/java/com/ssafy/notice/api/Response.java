package com.ssafy.notice.api;

import lombok.Getter;
import lombok.Setter;

public class Response {

    @Getter @Setter
    public static class noticeResponse{
        private String message;
        private Object data;
    }
}
