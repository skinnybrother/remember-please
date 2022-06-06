package com.smalldogg.rememberplease.domain.forecast.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@IdClass(ForecastId.class)
public class Forecast {
    @Id
    @Column(name = "x")
    private String x;
    @Id
    @Column(name = "y")
    private String y;

    private String city;
    private String state;
    private String town;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private ShortForecast shortForecast;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private VilageForecast vilageForecast;

    public void saveShortForecast(ShortForecast shortForecast){
        this.shortForecast = shortForecast;
    }

    public void saveVilageForecast(VilageForecast vilageForecast) {
        this.vilageForecast = vilageForecast;
    }
}
