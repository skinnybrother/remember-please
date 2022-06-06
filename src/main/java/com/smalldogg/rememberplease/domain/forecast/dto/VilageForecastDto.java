package com.smalldogg.rememberplease.domain.forecast.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VilageForecastDto {
    private float pop;
    private float pcp;
    private float sno;
    private float sky;
    private float tmp;
    private float tmn;
    private float tmx;
    private float wav;

    private LocalDateTime vilageReleaseDate;
}
