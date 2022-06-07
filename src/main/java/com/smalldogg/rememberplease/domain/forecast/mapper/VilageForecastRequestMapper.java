package com.smalldogg.rememberplease.domain.forecast.mapper;

import com.smalldogg.rememberplease.domain.GenericMapper;
import com.smalldogg.rememberplease.domain.forecast.dto.VilageForecastDto;
import com.smalldogg.rememberplease.domain.forecast.entity.VilageForecast;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VilageForecastRequestMapper extends GenericMapper<VilageForecastDto, VilageForecast> {
    VilageForecastRequestMapper INSTANCE = Mappers.getMapper(VilageForecastRequestMapper.class);

}
