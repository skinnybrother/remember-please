package com.smalldogg.rememberplease.domain.forecast.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class LocationDto {
    private String latitude;
    private String longitude;
    private String x;
    private String y;
}
