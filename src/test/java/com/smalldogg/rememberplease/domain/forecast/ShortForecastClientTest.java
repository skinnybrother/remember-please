package com.smalldogg.rememberplease.domain.forecast;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URISyntaxException;

@SpringBootTest
class ShortForecastClientTest {

    @Autowired
    ForecastClient forecastClient;

    @Test
    void forecastClientTest() throws URISyntaxException {
        forecastClient.getUltraShortForecast("60","121");
    }
}