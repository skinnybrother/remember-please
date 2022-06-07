package com.smalldogg.rememberplease.domain.forecast.dto;

import com.smalldogg.rememberplease.domain.forecast.entity.ShortForecast;
import com.smalldogg.rememberplease.domain.forecast.entity.VilageForecast;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForecastResponseDto {
    private String state;
    private String city;
    private String town;
    private ShortForecast shortForecast;
    private VilageForecast vilageForecast;
}
