package com.smalldogg.rememberplease.domain.forecast.entity;

import com.smalldogg.rememberplease.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@IdClass(ForecastId.class)
public class Forecast extends BaseTimeEntity implements Persistable<String> {

    @Id
    @Column(name = "x")
    private String x;
    @Id
    @Column(name = "y")
    private String y;
    private String city;
    private String state;
    private String town;
    private float pty;//강수형태
    private float reh;//습도
    private float rn1;//1시간 강수량
    private float t1h;//기온
    private float uuu;//동서바람성분
    private float vec;//풍향
    private float vvv;//남북바람성분
    private float wsd;//풍속

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}