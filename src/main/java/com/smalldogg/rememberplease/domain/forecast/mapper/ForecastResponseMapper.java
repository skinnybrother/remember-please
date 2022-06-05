package com.smalldogg.rememberplease.domain.forecast.mapper;

import com.smalldogg.rememberplease.domain.GenericMapper;
import com.smalldogg.rememberplease.domain.forecast.dto.ForecastDto;
import com.smalldogg.rememberplease.domain.forecast.dto.ForecastResponseDto;
import com.smalldogg.rememberplease.domain.forecast.entity.Forecast;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ForecastResponseMapper extends GenericMapper<ForecastResponseDto, Forecast> {
     ForecastResponseMapper INSTANCE = Mappers.getMapper(ForecastResponseMapper.class);
}
