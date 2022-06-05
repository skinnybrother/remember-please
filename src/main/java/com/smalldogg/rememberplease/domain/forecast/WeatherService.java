package com.smalldogg.rememberplease.domain.forecast;

import com.smalldogg.rememberplease.domain.forecast.dto.ForecastDto;
import com.smalldogg.rememberplease.domain.forecast.dto.LocationDto;
import com.smalldogg.rememberplease.domain.forecast.dto.ForecastResponseDto;
import com.smalldogg.rememberplease.domain.forecast.entity.Forecast;
import com.smalldogg.rememberplease.domain.forecast.entity.ForecastId;
import com.smalldogg.rememberplease.domain.forecast.mapper.ForecastRequestMapper;
import com.smalldogg.rememberplease.domain.forecast.mapper.ForecastResponseMapper;
import com.smalldogg.rememberplease.domain.forecast.repository.ForecastRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.smalldogg.rememberplease.domain.forecast.LocalDateTimeUtil.getNearestAvailableLocalDateTime;

@Slf4j
@Service
public class WeatherService {

    private final ForecastRepository forecastRepository;
    private final LocationExtractor locationExtractor;
    private final ForecastClient forecastClient;

    public WeatherService(ForecastRepository forecastRepository, LocationExtractor locationExtractor, ForecastClient forecastClient) {
        this.forecastRepository = forecastRepository;
        this.locationExtractor = locationExtractor;
        this.forecastClient = forecastClient;
    }

    public ForecastResponseDto getWeather(LocationDto locationDto) {
        ForecastDto location = locationExtractor.getLocation(locationDto.getLongitude(), locationDto.getLatitude());
        ForecastId forecastId = new ForecastId(locationDto.getX(), locationDto.getY());
        Optional<Forecast> forecastOptional = forecastRepository.findById(forecastId);

        LocalDateTime nearestAvailableLocalDateTime = getNearestAvailableLocalDateTime();
        Optional<Forecast> latestAvailbleForecast = forecastRepository.findByXAndYAndReleaseDateAfter(locationDto.getX(), locationDto.getY(), nearestAvailableLocalDateTime);

        Forecast forecast;
        if (latestAvailbleForecast.isEmpty()) {
            ForecastDto forecastDto = forecastClient.getForecast(locationDto.getX(), locationDto.getY());

            forecastDto.setX(locationDto.getX());
            forecastDto.setY(locationDto.getY());
            forecastDto.setState(location.getState());
            forecastDto.setCity(location.getCity());
            forecastDto.setTown(location.getTown());
            forecastDto.setReleaseDate(nearestAvailableLocalDateTime);
            forecast = ForecastRequestMapper.INSTANCE.toEntity(forecastDto);

            forecastRepository.save(forecast);
        } else {
            forecast = forecastOptional.get();
        }

        return ForecastResponseMapper.INSTANCE.toDto(forecast);
    }


}
