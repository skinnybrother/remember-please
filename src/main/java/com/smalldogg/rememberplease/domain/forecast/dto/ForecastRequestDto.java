package com.smalldogg.rememberplease.domain.forecast.dto;

import com.smalldogg.rememberplease.domain.BaseTimeEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForecastRequestDto extends BaseTimeEntity {
    private String x;
    private String y;
    private String city;
    private String state;
    private String town;
}
