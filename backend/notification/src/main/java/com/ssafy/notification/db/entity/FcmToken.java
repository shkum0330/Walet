package com.ssafy.notification.db.entity;


import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class FcmToken extends BaseTimeEntity{
    @Id
    @Column(name = "fcm_id")
    private Long fcmId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "token",length = 100)
    private String token;

}
