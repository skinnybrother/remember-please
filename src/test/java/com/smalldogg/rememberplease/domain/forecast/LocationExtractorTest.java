package com.smalldogg.rememberplease.domain.forecast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.smalldogg.rememberplease.domain.forecast.dto.ForecastRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LocationExtractorTest {

    @Test
    @DisplayName("위도 및 경도 데이터를 기반으로 위치 값을 가지고 오는 메서드 테스트")
    void getLocationTest() throws JsonProcessingException {
        LocationExtractor locationExtractor = new LocationExtractor();
        ForecastRequestDto location = locationExtractor.getForecast("126.92738", "37.59678");
        System.out.println("location = " + location);
    }
}