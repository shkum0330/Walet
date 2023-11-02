package com.ssafy.member.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;


public class MemberDto {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class MemberRequest{
        private Long id;
        private String name;
        private String email;
        private String password;
        private String phoneNumber;
        private String birth;
        private String fingerPrint;
        private String pinNumber;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class UserResponse{
        private Long id;
        private String name;
        private String email;
        private String phoneNumber;
        private String birth;
        private LocalDateTime createdDate;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class UsersResponse{
        private Long id;
        private String name;
        private String email;
        private LocalDateTime createdDate;
    }


}
