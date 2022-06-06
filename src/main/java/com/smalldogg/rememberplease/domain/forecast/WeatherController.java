package com.smalldogg.rememberplease.domain.forecast;

import com.smalldogg.rememberplease.domain.forecast.dto.LocationDto;
import com.smalldogg.rememberplease.domain.forecast.dto.ForecastResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeatherController {
    private final ForecastService forecastService;


    public WeatherController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/weather")
    public ForecastResponseDto getWeather(LocationDto locationDto) {

        return forecastService.getForecast(locationDto);
    }
}
