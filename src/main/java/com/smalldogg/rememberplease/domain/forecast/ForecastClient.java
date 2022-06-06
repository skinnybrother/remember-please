package com.smalldogg.rememberplease.domain.forecast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.smalldogg.rememberplease.domain.forecast.dto.ShortForecastDto;
import com.smalldogg.rememberplease.domain.forecast.dto.VilageForecastDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Stream;

import static com.smalldogg.rememberplease.domain.forecast.LocalDateTimeUtil.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Component
public class ForecastClient {
    @Value("${openapi.weather.servicekey}")
    private String SERVICE_KEY;
    @Value("${openapi.weather.endpoint}")
    private String ENDPOINT;

    //1시간 마다, 매 40분에 신규 데이터 생성(온도)
    private String ULTRA_SHORT_FORECAST;
    //매일 오전 2시부터 3시간 간격으로 신규 데이터 생성(강수)
    private String VILAGE_FORECAST;
    private static final String DATA_TYPE = "JSON";

    private static final List<String> NONE_TYPE = Arrays.asList("강수없음", "적설없음");

    @PostConstruct
    public void init() {
        //1시간 마다, 매 40분에 신규 데이터 생성(온도)
        this.ULTRA_SHORT_FORECAST = ENDPOINT + "getUltraSrtNcst";
        //매일 오전 2시부터 3시간 간격으로 신규 데이터 생성(강수)
        this.VILAGE_FORECAST = ENDPOINT + "getVilageFcst";
    }

    public ShortForecastDto getUltraShortForecast(String x, String y) {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .uriTemplateHandler(getDefaultUriBuilderFactory())
                .build();

        UriComponents uri = getUri(ULTRA_SHORT_FORECAST, x, y);
        ResponseEntity<String> forecastResponse = restTemplate.getForEntity(uri.toUriString(), String.class);

        ShortForecastDto shortForecastDto = convertForecast(forecastResponse);
        shortForecastDto.setReleaseDate(getShortNearestAvailableLocalDateTime());
        //결과 파싱
        return shortForecastDto;
    }

    public VilageForecastDto getVilageForecast(String x, String y) {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .uriTemplateHandler(getDefaultUriBuilderFactory())
                .build();

        UriComponents uri = getUri(VILAGE_FORECAST, x, y);
        ResponseEntity<String> forecastResponse = restTemplate.getForEntity(uri.toUriString(), String.class);
        VilageForecastDto vilageForecastDto = convertVilageForcast(forecastResponse);
        vilageForecastDto.setVilageReleaseDate(getVilageNearestAvailableLocalDateTime());
        return vilageForecastDto;
    }

    private DefaultUriBuilderFactory getDefaultUriBuilderFactory() {
        DefaultUriBuilderFactory builderFactory = new DefaultUriBuilderFactory();
        // URI의 내용을 인코딩하지 않도록 인코딩 모드 적용
        builderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        return builderFactory;
    }

    private UriComponents getUri(String url, String x, String y) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("serviceKey", SERVICE_KEY)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "36")
                .queryParam("base_date", getLocalDate())
                .queryParam("nx", x)
                .queryParam("ny", y)
                .queryParam("dataType", DATA_TYPE);

        if (url == ULTRA_SHORT_FORECAST) {
            uriComponentsBuilder.queryParam("base_time", getShortNearestAvailableTime());
        } else {
            uriComponentsBuilder.queryParam("base_time", getVilageNearestAvailableLocalDateTime());
        }
        return uriComponentsBuilder.build();
    }


    private ShortForecastDto convertForecast(ResponseEntity<String> forecastResponse) {
        Map<String, Float> forecastMap = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonElement element = JsonParser.parseString(forecastResponse.getBody());
        JsonArray items = element.getAsJsonObject()
                .get("response").getAsJsonObject()
                .get("body").getAsJsonObject()
                .get("items").getAsJsonObject()
                .getAsJsonArray("item");

        items.iterator().forEachRemaining(e -> forecastMap.put(
                e.getAsJsonObject().get("category").getAsString().toLowerCase(),
                Float.valueOf(e.getAsJsonObject().get("obsrValue").getAsString())
        ));

        return objectMapper.convertValue(forecastMap, ShortForecastDto.class);
    }

    private VilageForecastDto convertVilageForcast(ResponseEntity<String> forecastResponse) {
        Map<String, Float> forecastMap = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonElement element = JsonParser.parseString(forecastResponse.getBody());
        JsonArray items = element.getAsJsonObject()
                .get("response").getAsJsonObject()
                .get("body").getAsJsonObject()
                .get("items").getAsJsonObject()
                .getAsJsonArray("item");

        Iterator<JsonElement> iterator = items.iterator();

        Map<String, Float> maps = new HashMap<>();
        while (iterator.hasNext()) {
            JsonElement e = iterator.next();
            String fcstValue = e.getAsJsonObject().get("fcstValue").getAsString();
            Float fcstValueFloat = !NONE_TYPE.contains(fcstValue) ? Float.valueOf(fcstValue) : 0.0F;

            Float category = maps.get(e.getAsJsonObject().get("category").getAsString().toLowerCase());
            if (category == null) category = 0.0F;
            category += fcstValueFloat;

            maps.put(
                    e.getAsJsonObject().get("category").getAsString().toLowerCase(),
                    category
            );
        }
        maps.forEach((s,f)->f=f/3);

        System.out.println("maps = " + maps);
        return null;
    }
}
