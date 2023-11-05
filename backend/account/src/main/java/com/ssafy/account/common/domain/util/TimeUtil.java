package com.ssafy.account.common.domain.util;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Component
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

    public String calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);
        return String.valueOf(period.getYears());
    }

    public String YMD(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern("YYYYMMdd"));
    }

    public String HMS(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern("HHmmss"));
    }

    public String YMDHMS(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern("YYYYMMddHHmmss"));
    }

}
