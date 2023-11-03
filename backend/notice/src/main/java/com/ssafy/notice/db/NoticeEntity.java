package com.ssafy.notice.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity(name = "notice")
@Getter @Setter
public class NoticeEntity extends BaseTimeEntity {


    @Column(name = "notice_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name= "content")
    private String content;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "banner_img")
    private String bannerImg;


}

