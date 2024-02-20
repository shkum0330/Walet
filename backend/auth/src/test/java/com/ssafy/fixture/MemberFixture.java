package com.ssafy.fixture;

import com.ssafy.member.db.Member;
import com.ssafy.member.db.Role;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum MemberFixture {
    DEFAULT(
            1L,"김민지","minji@naver.com"
            ,"1234","010-1234-1234","2004-05-07",Role.USER,null,"000000"
    );
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String birth;
    private Role role;
    private String fingerPrint;
    private String pinNumber;

    MemberFixture(
            Long id,
        String name,
        String email,
        String password,
        String phoneNumber,
        String birth,
        Role role,
        String fingerPrint,
        String pinNumber
    ){
        this.id=id;
        this.name=name;
        this.email=email;
        this.password=password;
        this.phoneNumber=phoneNumber;
        this.birth=birth;
        this.role=role;
        this.fingerPrint=fingerPrint;
        this.pinNumber=pinNumber;
    }

    public Member getMember(){
        return Member.builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .birth(birth)
                .role(Role.USER)
                .pinNumber(pinNumber)
                .fingerPrint(fingerPrint)
                .build();
    }
}
