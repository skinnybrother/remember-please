package com.smalldogg.rememberplease.domain.forecast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class LocalDateTimeUtil {

    private static final String[] vilageAvailableTime = {"210", "510", "810", "1110", "1410", "1710", "2010", "2310"};

    public static LocalDateTime getShortNearestAvailableLocalDateTime() {
        DateTimeFormatter dateTimeFormatter = getDateTimeFormatter();
        String nearestAvailableLocalDateTime = getNearestLocalDateTime(getShortNearestAvailableTime());
        return LocalDateTime.parse(nearestAvailableLocalDateTime, dateTimeFormatter);
    }

    public static LocalDateTime getVilageNearestAvailableLocalDateTime() {
        DateTimeFormatter dateTimeFormatter = getDateTimeFormatter();
        String nearestAvailableLocalDateTime = getNearestLocalDateTime(getVilageNearestAvailableTime());
        return LocalDateTime.parse(nearestAvailableLocalDateTime, dateTimeFormatter);
    }

    private static String getNearestLocalDateTime(String nearestAvailableTime) {
        String nearestAvailableLocalDateTime = getLocalDate() + nearestAvailableTime;
        return nearestAvailableLocalDateTime;
    }

    private static DateTimeFormatter getDateTimeFormatter() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return dateTimeFormatter;
    }

    public static String getLocalDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDateTime.now().toLocalDate();

        if (LocalTime.now().isBefore(LocalTime.of(0, 40))) {
            localDate = localDate.minusDays(1L);
        }
        return localDate.format(dateTimeFormatter);
    }

    public static String getShortNearestAvailableTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HHmm");
        LocalTime nowTime = LocalDateTime.now().toLocalTime();
        //초단기실황정보는 매시간 40분마다 최신 정보를 업데이트 한다.
        if (nowTime.getMinute() < 40) {
            nowTime = nowTime.minusHours(1L);
        }
        nowTime = nowTime.minusMinutes(nowTime.getMinute());
        return nowTime.format(dateTimeFormatter);
    }

    public static String getVilageNearestAvailableTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HHmm");
        int nowTime = Integer.parseInt(LocalDateTime.now().toLocalTime().format(dateTimeFormatter));
        int first = Stream.of(vilageAvailableTime)
                .mapToInt(Integer::parseInt)
                .filter(i -> i < nowTime)
                .max().orElse(2310);
        return first < 1210 ? "0" + first/100*100 : String.valueOf(first/100*100);
    }
}
