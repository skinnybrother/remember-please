package com.smalldogg.rememberplease.domain.forecast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtil {

    public static LocalDateTime getNearestAvailableLocalDateTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String nearestAvailableLocalDateTime = getLocalDate() + getNearestAvailableTime();
        return LocalDateTime.parse(nearestAvailableLocalDateTime, dateTimeFormatter);
    }

    public static String getLocalDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDateTime.now().toLocalDate();

        if (LocalTime.now().isBefore(LocalTime.of(0,40))) {
            localDate.minusDays(1L);
        }
        return localDate.format(dateTimeFormatter);
    }

    public static String getNearestAvailableTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HHmm");
        LocalTime nowTime = LocalDateTime.now().toLocalTime();
        //초단기실황정보는 매시간 40분마다 최신 정보를 업데이트 한다.
        if(nowTime.getMinute()<40){
            nowTime = nowTime.minusHours(1L);
        }
        nowTime = nowTime.minusMinutes(nowTime.getMinute());
        return nowTime.format(dateTimeFormatter);
    }
}
