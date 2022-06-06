package com.smalldogg.rememberplease.domain.forecast.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class VilageForecast {

    @Id @GeneratedValue
    private Long id;

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
