package com.smalldogg.rememberplease.domain.forecast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTest {

    LocalDateTime localDateTime;

    @BeforeEach
    void getLocalDateTime() {
        this.localDateTime = LocalDateTime.now();
    }

    @Test
    void getDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String localDate = localDateTime.toLocalDate().format(dateTimeFormatter);
        System.out.println("localDate = " + localDate);
    }

    @Test
    void getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HHmm");
        String localDate = localDateTime.toLocalTime().format(dateTimeFormatter);
        System.out.println("localDate = " + localDate);
    }
}
