package com.ssafy.notice.api;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class noticeDTO {
    @Getter @Setter
    public static class request{
        private Long id;
        private String title;
        private String subTitle;
        private String content;
        private Boolean isActive;
        private String bannerImg;
        private LocalDateTime registerTime;
        private LocalDateTime modifyTime;

        public String getRegisterTime() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
            return registerTime != null ? registerTime.format(formatter) : null;
        }

        public String getModifyTime() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
            return modifyTime != null ? modifyTime.format(formatter) : null;
        }
    }

}
