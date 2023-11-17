package com.ssafy.service.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeUtil {
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
