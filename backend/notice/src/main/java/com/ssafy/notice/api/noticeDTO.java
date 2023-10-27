package com.ssafy.notice.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class noticeDTO {

    @NoArgsConstructor
    @Getter @Setter
    public static class request{
        private Long id;
        private String title;
        private String content;
        private Boolean isActive;
        private LocalDateTime registerTime;
        private LocalDateTime modifyTime;

        public request(Long id, String title, String content, Boolean isActive, LocalDateTime registerTime, LocalDateTime modifyTime){
            this.id = id;
            this.title = title;
            this.content = content;
            this.isActive = isActive;
            this.registerTime = registerTime;
            this.modifyTime = modifyTime;
        }

        public String getRegisterTime() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
            return registerTime != null ? registerTime.format(formatter) : null;
        }

        public String getModifyTime() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
            return modifyTime != null ? modifyTime.format(formatter) : null;
        }
    }

    @Getter @Setter
    public static class noticeResponse{
        private String message;
        private Object data;
    }
}
