package com.smalldogg.rememberplease.domain.forecast.repository;

import com.smalldogg.rememberplease.domain.forecast.dto.WeatherResponseDto;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class WeatherRepositoryImpl implements WeatherRepositoryCustom{
    @Override
    public Optional<WeatherResponseDto> findByIdLatest(String id) {
        return Optional.empty();
    }
//
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public Optional<WeatherResponseDto> findByIdLatest(WeatherSeachCondition weatherDto) {
//        queryFactory
//                .select(new WeatherDto(
//
//                ))
//                .where(weather.id.eq(id));
//        return Optional.empty();
//    }
}
