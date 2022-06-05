package com.smalldogg.rememberplease.domain.forecast.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ForecastId implements Serializable {
    private static final long serialVersionUID = 860971432615315697L;
    @Column(name ="x")
    private String x;
    @Column(name ="y")
    private String y;

}
