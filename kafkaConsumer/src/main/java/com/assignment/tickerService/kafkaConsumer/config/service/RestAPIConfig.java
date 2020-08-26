package com.assignment.tickerService.kafkaConsumer.config.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestAPIConfig {

    @Bean
    RestTemplate bondLinkBridgeRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
