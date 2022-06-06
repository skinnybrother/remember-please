package com.smalldogg.rememberplease.domain.forecast;

import com.smalldogg.rememberplease.domain.forecast.dto.ForecastResponseDto;
import com.smalldogg.rememberplease.domain.forecast.dto.LocationDto;
import com.smalldogg.rememberplease.domain.forecast.repository.ForecastRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ShortForecastServiceTest {

    @Autowired
    ForecastService forecastService;

    @Autowired
    ForecastRepository forecastRepository;

    LocationDto locationDto;

    @BeforeEach
    void setUp() {
        this.locationDto = new LocationDto();
        locationDto.setLatitude("37.3190288");
        locationDto.setLongitude("126.9782842");
        locationDto.setX("60");
        locationDto.setY("121");
    }

    @Test
    void getWeather(){
        ForecastResponseDto weather = forecastService.getForecast(locationDto);
        System.out.println("weather = " + weather);
    }

    @Test
    public void WeatherTest(){
    }

}