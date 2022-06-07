package com.smalldogg.rememberplease.domain.forecast.repository;

import com.smalldogg.rememberplease.domain.forecast.entity.Forecast;
import com.smalldogg.rememberplease.domain.forecast.entity.ShortForecast;
import com.smalldogg.rememberplease.domain.forecast.entity.ForecastId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ForecastRepository extends JpaRepository<Forecast, ForecastId> {

}
