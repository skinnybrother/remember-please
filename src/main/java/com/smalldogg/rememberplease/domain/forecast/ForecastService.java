package com.smalldogg.rememberplease.domain.forecast;

import com.smalldogg.rememberplease.domain.forecast.dto.*;
import com.smalldogg.rememberplease.domain.forecast.entity.Forecast;
import com.smalldogg.rememberplease.domain.forecast.entity.ForecastId;
import com.smalldogg.rememberplease.domain.forecast.mapper.ForecastRequestMapper;
import com.smalldogg.rememberplease.domain.forecast.mapper.ShortForecastRequestMapper;
import com.smalldogg.rememberplease.domain.forecast.repository.ForecastRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.smalldogg.rememberplease.domain.forecast.LocalDateTimeUtil.getShortNearestAvailableLocalDateTime;
import static com.smalldogg.rememberplease.domain.forecast.LocalDateTimeUtil.getVilageNearestAvailableLocalDateTime;

@Slf4j
@Service
public class ForecastService {

    private final ForecastRepository forecastRepository;
    private final LocationExtractor locationExtractor;
    private final ForecastClient forecastClient;

    public ForecastService(ForecastRepository forecastRepository, LocationExtractor locationExtractor, ForecastClient forecastClient) {
        this.forecastRepository = forecastRepository;
        this.locationExtractor = locationExtractor;
        this.forecastClient = forecastClient;
    }

    public ForecastResponseDto getForecast(LocationDto locationDto) {
        Optional<Forecast> forecastOptional = forecastRepository.findById(new ForecastId(locationDto.getX(), locationDto.getY()));
        Forecast forecast = forecastOptional.orElseGet(() -> {
            ForecastRequestDto forecastRequestDto = locationExtractor.getForecast(locationDto.getLongitude(), locationDto.getLatitude());
            forecastRequestDto.setX(locationDto.getX());
            forecastRequestDto.setY(locationDto.getY());
            return forecastRepository.save(ForecastRequestMapper.INSTANCE.toEntity(forecastRequestDto));
        });

        if (forecast.getShortForecast() == null ||
                forecast.getShortForecast().getReleaseDate().isBefore(getShortNearestAvailableLocalDateTime())) {

            ShortForecastDto shortForecastDto = forecastClient.getUltraShortForecast(locationDto.getX(), locationDto.getY());
            forecast.saveShortForecast(ShortForecastRequestMapper.INSTANCE.toEntity(shortForecastDto));
            forecastRepository.save(forecast);
        }

        if (forecast.getVilageForecast() == null ||
                forecast.getVilageForecast().getReleaseDate().isBefore(getVilageNearestAvailableLocalDateTime())) {
            VilageForecastDto vilageForecastDto = forecastClient.getVilageForecast(locationDto.getX(), locationDto.getY());
        }
        return null;
    }

    //1시간 주기


//        return ForecastResponseMapper.INSTANCE.toDto(forecast);
}
