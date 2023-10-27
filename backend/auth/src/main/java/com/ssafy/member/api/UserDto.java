package com.ssafy.member.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response{
        private String name;
        private String email;
        private String phoneNumber;
        private String birth;
        private LocalDateTime createdDate;
    }
}
