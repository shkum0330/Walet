package com.ssafy.member.db;



import com.ssafy.global.PasswordEncoder;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Member extends BaseTimeEntity {

    @Id @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth")
    private String birth;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "finger_print")
    private String fingerPrint;

    @Column(name = "pin_number")
    private String pinNumber;


    @Builder
    public Member(Long id,String name, String email, String password, String phoneNumber,
                  String birth, Role role, String fingerPrint, String pinNumber) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.isDeleted=false;
        this.role = role;
        this.fingerPrint = fingerPrint;
        this.pinNumber = pinNumber;
    }


    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
