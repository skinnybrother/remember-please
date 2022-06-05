package com.smalldogg.rememberplease.domain.forecast.repository;

import com.smalldogg.rememberplease.domain.forecast.entity.Forecast;
import com.smalldogg.rememberplease.domain.forecast.entity.ForecastId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForecastRepository extends JpaRepository<Forecast, ForecastId> {

}
