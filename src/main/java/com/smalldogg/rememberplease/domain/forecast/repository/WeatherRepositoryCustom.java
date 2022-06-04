package com.smalldogg.rememberplease.domain.forecast.repository;

import com.smalldogg.rememberplease.domain.forecast.dto.WeatherResponseDto;

import java.util.Optional;

public interface WeatherRepositoryCustom {
    Optional<WeatherResponseDto> findByIdLatest(String id);
}
