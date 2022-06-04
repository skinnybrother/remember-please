package com.smalldogg.rememberplease.domain.forecast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherResponseDto {
    private String id;
    private float tempAvg;
    private float temp;
}
