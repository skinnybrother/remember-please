package com.smalldogg.rememberplease.domain.forecast.mapper;

import com.smalldogg.rememberplease.domain.GenericMapper;
import com.smalldogg.rememberplease.domain.forecast.dto.ForecastDto;
import com.smalldogg.rememberplease.domain.forecast.entity.Forecast;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ForecastRequestMapper extends GenericMapper<ForecastDto, Forecast> {
     ForecastRequestMapper INSTANCE = Mappers.getMapper(ForecastRequestMapper.class);
}
