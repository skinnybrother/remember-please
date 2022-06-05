package com.smalldogg.rememberplease.domain.forecast;

import com.smalldogg.rememberplease.domain.forecast.dto.LocationDto;
import com.smalldogg.rememberplease.domain.forecast.dto.ForecastResponseDto;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class WeatherController {
    private final WeatherService weatherService;


    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public ForecastResponseDto getWeather(LocationDto locationDto, HttpServletRequest request) throws IOException {

        return weatherService.getWeather(locationDto);
    }
}
