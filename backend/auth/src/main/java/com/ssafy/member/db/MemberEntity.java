package com.ssafy.member.db;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "member")
@NoArgsConstructor
@Getter @Setter
public class MemberEntity extends BaseTimeEntity {


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

    @Column(name = "random_member_id", unique = true)
    private String randomMemberId;

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
