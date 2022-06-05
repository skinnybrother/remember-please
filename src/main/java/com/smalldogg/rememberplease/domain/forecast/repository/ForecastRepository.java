package com.smalldogg.rememberplease.domain.forecast.repository;

import com.smalldogg.rememberplease.domain.forecast.entity.Forecast;
import com.smalldogg.rememberplease.domain.forecast.entity.ForecastId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ForecastRepository extends JpaRepository<Forecast, ForecastId> {
    Optional<Forecast> findByXAndYAndReleaseDateAfter(String x, String y, LocalDateTime releaseDate);
}
