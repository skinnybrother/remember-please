package com.smalldogg.rememberplease.domain.forecast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.smalldogg.rememberplease.domain.forecast.dto.ForecastDto;
import com.smalldogg.rememberplease.domain.forecast.entity.Forecast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.smalldogg.rememberplease.domain.forecast.LocalDateTimeUtil.getLocalDate;
import static com.smalldogg.rememberplease.domain.forecast.LocalDateTimeUtil.getNearestAvailableTime;
import static java.util.stream.Collectors.toMap;

@Component
public class ForecastClient {
    @Value("${openapi.weather.servicekey}")
    private String SERVICE_KEY;

    @Value("${openapi.weather.endpoint}")
    private String ENDPOINT;

    private static final String DATA_TYPE = "JSON";

    public ForecastDto getForecast(String x, String y) {
        DefaultUriBuilderFactory builderFactory = new DefaultUriBuilderFactory();
        // URI의 내용을 인코딩하지 않도록 인코딩 모드 적용
        builderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        RestTemplate restTemplate = new RestTemplateBuilder()
                .uriTemplateHandler(builderFactory)
                .build();

        UriComponents uri = getUri(x, y);
        ResponseEntity<String> forecastResponse = restTemplate.getForEntity(uri.toUriString(), String.class);

        if(forecastResponse.getStatusCode()== HttpStatus.OK){
            System.out.println("Okay");
        }
        //결과 파싱
        return convertForecast(forecastResponse);
    }

    private UriComponents getUri(String x, String y) {
        return UriComponentsBuilder.fromHttpUrl(ENDPOINT)
                .queryParam("serviceKey", SERVICE_KEY)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "1000")
                .queryParam("base_date", getLocalDate())
                .queryParam("base_time", getNearestAvailableTime())
                .queryParam("nx", x)
                .queryParam("ny", y)
                .queryParam("dataType", DATA_TYPE)
                .build();
    }



    private ForecastDto convertForecast(ResponseEntity<String> forecastResponse) {
        Map<String, Float> forecastMap = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonElement element = JsonParser.parseString(forecastResponse.getBody());
        JsonArray items = element.getAsJsonObject()
                .get("response").getAsJsonObject()
                .get("body").getAsJsonObject()
                .get("items").getAsJsonObject()
                .getAsJsonArray("item");

        for (JsonElement item : items) {
            String key = item.getAsJsonObject().get("category").getAsString().toLowerCase();
            float value = Float.valueOf(item.getAsJsonObject().get("obsrValue").getAsString());
            forecastMap.put(key, value);
        }

        return objectMapper.convertValue(forecastMap, ForecastDto.class);
    }


}
