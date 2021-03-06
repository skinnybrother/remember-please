package com.smalldogg.rememberplease.domain.forecast.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VilageForecastDto {
    private float pop;
    private float pcp;
    private float sno;
    private float sky;
    private float tmp;
    private float tmn;
    private float tmx;
    private float wav;

    private LocalDateTime releaseDate;
}
