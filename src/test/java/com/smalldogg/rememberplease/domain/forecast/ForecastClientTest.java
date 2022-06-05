package com.smalldogg.rememberplease.domain.forecast;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URISyntaxException;

@SpringBootTest
class ForecastClientTest {

    @Autowired
    ForecastClient forecastClient;

    @Test
    void forecastClientTest() throws URISyntaxException {
        forecastClient.getForecast("60","121");
    }
}