package com.smalldogg.rememberplease.domain.forecast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.smalldogg.rememberplease.domain.forecast.dto.LocationDto;
import com.smalldogg.rememberplease.domain.forecast.dto.WeatherResponseDto;
import com.smalldogg.rememberplease.domain.forecast.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final LocationExtractor locationExtractor;
    private final ForecastClient forecastClient;

    public WeatherService(WeatherRepository weatherRepository, LocationExtractor locationExtractor, ForecastClient forecastClient) {
        this.weatherRepository = weatherRepository;
        this.locationExtractor = locationExtractor;
        this.forecastClient = forecastClient;
    }

    public WeatherResponseDto getWeather(LocationDto locationDto) {
        String id = locationDto.getId();
        if(!StringUtils.hasText(id)){
            try {
                id = locationExtractor.getLocation(locationDto.getLongitude(), locationDto.getLatitude());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        Optional<WeatherResponseDto> weatherResponseDto = weatherRepository.findByIdLatest(id);

        if(weatherResponseDto.isEmpty()){
            forecastClient.getForecast(locationDto.getX(), locationDto.getY());
        }

        return weatherResponseDto.orElse(null);
    }


}
