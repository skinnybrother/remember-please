package com.smalldogg.rememberplease.domain.forecast.dto;

import com.smalldogg.rememberplease.domain.BaseTimeEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForecastDto extends BaseTimeEntity {
    private String id;
    private String city;
    private String state;
    private String town;
    private String x;
    private String y;
    private LocalDateTime releaseDate;
    private float pty;//강수형태
    private float reh;//습도
    private float rn1;//1시간 강수량
    private float t1h;//기온
    private float uuu;//동서바람성분
    private float vec;//풍향
    private float vvv;//남북바람성분
    private float wsd;//풍속


}
