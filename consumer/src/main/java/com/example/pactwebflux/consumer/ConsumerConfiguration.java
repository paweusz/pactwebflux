package com.example.pactwebflux.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ConsumerConfiguration {

    @Bean
    WebClient webClient(@Value("${provider.url}") String providerURL) {
        return WebClient.create(providerURL);
    }

}
