package com.smalldogg.rememberplease.domain.forecast.mapper;

import com.smalldogg.rememberplease.domain.GenericMapper;
import com.smalldogg.rememberplease.domain.forecast.dto.ShortForecastDto;
import com.smalldogg.rememberplease.domain.forecast.entity.ShortForecast;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShortForecastRequestMapper extends GenericMapper<ShortForecastDto, ShortForecast> {
    ShortForecastRequestMapper INSTANCE = Mappers.getMapper(ShortForecastRequestMapper.class);

}
