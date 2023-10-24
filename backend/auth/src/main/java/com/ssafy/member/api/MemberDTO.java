package com.ssafy.member.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class MemberDTO {
    @NoArgsConstructor
    @Getter @Setter
    public static class request{
        private Long id;
        private String name;
        private String email;
        private String password;
        private String phoneNumber;
        private String birth;

        public request(Long id, String name, String email, String password, String phoneNumber, String birth){
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
            this.phoneNumber = phoneNumber;
            this.birth = birth;
        }


    }
    @Getter @Setter
    public static class noticeResponse{
        private String message;
        private Object data;
    }
}
