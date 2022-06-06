package com.smalldogg.rememberplease.domain.forecast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class LocalDateTimeUtil {

    private static final String[] availableTime = {"200", "500", "800", "1100", "1400", "1700", "2000", "2300"};

    public static LocalDateTime getShortNearestAvailableLocalDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String nearestAvailableLocalDateTime = getLocalDate() + getShortNearestAvailableTime();
        return LocalDateTime.parse(nearestAvailableLocalDateTime, dateTimeFormatter);
    }

    public static LocalDateTime getVilageNearestAvailableLocalDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String nearestAvailableLocalDateTime = getLocalDate() + getVilageNearestAvailableTime();
        return LocalDateTime.parse(nearestAvailableLocalDateTime, dateTimeFormatter);
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

    private static String getVilageNearestAvailableTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HHmm");
        int nowTime = Integer.parseInt(LocalDateTime.now().toLocalTime().format(dateTimeFormatter));
        int first = Stream.of(availableTime)
                .mapToInt(Integer::parseInt)
                .filter(i -> i < nowTime)
                .max().orElse(2300);
        return first < 1200 ? "0" + first : String.valueOf(first);
    }
}
