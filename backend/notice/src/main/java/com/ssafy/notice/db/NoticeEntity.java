package com.ssafy.notice.db;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity(name = "notice")
@Getter
public class NoticeEntity extends BaseTimeEntity {


    @Column(name = "notice_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name= "content")
    private String content;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "banner_img")
    private String bannerImg;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }
}

