package com.smalldogg.rememberplease.domain.forecast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.smalldogg.rememberplease.domain.forecast.dto.ForecastDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class LocationExtractor {
    @Value("${ncp.maps.reverse-geocoding.url}")
    private String geocodingUrl;
    @Value("${ncp.maps.client.ID}")
    private String clientId;
    @Value("${ncp.maps.client.secret}")
    private String clientSecret;

    public ForecastDto getLocation(String longitude, String latitude) {
        RestTemplate restTemplate = new RestTemplate();

        List<Object> objects = new LinkedList<>();

        UriComponents uri = getUri(longitude, latitude);
        HttpEntity entity = new HttpEntity(getNCPHeader());

        ResponseEntity<String> exchange = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entity, String.class);
        return extractLocationList(exchange.getBody());

    }

    private HttpHeaders getNCPHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-NCP-APIGW-API-KEY-ID", clientId);
        httpHeaders.add("X-NCP-APIGW-API-KEY", clientSecret);
        return httpHeaders;
    }

    private UriComponents getUri(String longitude, String latitude) {
        return UriComponentsBuilder.fromHttpUrl(geocodingUrl)
                .queryParam("coords", (longitude + "," + latitude).replace(" ", ""))
                .queryParam("orders", "legalcode")
                .queryParam("output", "json")
                .build();
    }

    private ForecastDto extractLocationList(String jsonString) {
        ForecastDto forecastDto = new ForecastDto();
        JsonObject locationJson = JsonParser.parseString(jsonString).getAsJsonObject()
                .get("results").getAsJsonArray().get(0).getAsJsonObject()
                .get("region").getAsJsonObject();

        for (int i = 1; i < 4; i++) {
            if (i == 1) forecastDto.setState(locationJson.get("area" + i).getAsJsonObject().get("name").toString());
            if (i == 2) forecastDto.setCity(locationJson.get("area" + i).getAsJsonObject().get("name").toString());
            if (i == 3) forecastDto.setTown(locationJson.get("area" + i).getAsJsonObject().get("name").toString());
        }
        return forecastDto;
    }
}
