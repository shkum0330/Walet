package com.ssafy.account.common.domain.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public String dateTypeConverter(LocalDateTime dateTime){
        Duration duration = Duration.between(dateTime, LocalDateTime.now());

        if (duration.toDays() > 0) {
            return duration.toDays() + "일 전";
        } else if (duration.toHours() > 0) {
            return duration.toHours() + "시간 전";
        } else if (duration.toMinutes() > 0) {
            return duration.toMinutes() + "분 전";
        } else {
            return duration.toSeconds() + "초 전";
        }
    }

    public String dateTypeFormatter(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm분:ss초"));
    }

}
