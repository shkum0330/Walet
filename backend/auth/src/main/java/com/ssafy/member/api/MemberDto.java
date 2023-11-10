package com.ssafy.member.api;

import com.ssafy.member.db.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


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
        private List<accountResponse.AccountData> account;

        public String getCreatedDate() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
            return createdDate != null ? createdDate.format(formatter) : null;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class UsersResponse{
        private Long id;
        private String name;
        private String email;
        private String phoneNumber;
        private LocalDateTime createdDate;
        private List<accountResponse.AccountData> account;

        public UsersResponse(MemberEntity member) {
            this.id = member.getId();
            this.name = member.getName();
            this.email = member.getEmail();
            this.phoneNumber = member.getPhoneNumber();
            this.createdDate = member.getCreatedDate();
        }

        public String getCreatedDate() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
            return createdDate != null ? createdDate.format(formatter) : null;
        }
    }

    @Data
    public static class NameResponse{
        private String name;
    }


    @Data
    public static class PhoneResponse{
        private String phoneNumber;
    }

    @Data
    public static class CountResponse{
        private String newUser;
        private long allUsers;
        private long generalAccountCount;
        private long petAccountCount;
    }

    @Data
    public static class accountResponse{
        private int code;
        private String message;
        private List<AccountData> data;

        @Data
        public static class AccountData {
            private long accountId;
            private long memberId;
            private String accountName;
            private String accountNumber;
            private int balance;
        }
    }

    @Data
    public static class TransactionResponse {
        private int code;
        private String message;
        private CountData data;

        @Data
        public static class CountData {
            private Long generalAccountCount;
            private Long petAccountCount;
        }
    }

    @Data
    public static class transactionRequest{
        private List<Long> allMemberIds;
    }
}
