package com.smalldogg.rememberplease.domain.forecast;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTimeUtilTest {

    @Test
    void getVilageNearestAvailableLocalDateTime() {
        long startTime = System.currentTimeMillis();
        LocalDateTimeUtil.getVilageNearestAvailableLocalDateTime();
        long endTime = System.currentTimeMillis();
        System.out.println("(endTime-startTime) = " + (endTime-startTime));
    }
}