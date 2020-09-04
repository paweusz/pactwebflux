package com.example.pactwebflux.consumer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ConsumerAdapter {

    WebClient webClient;

    public Flux<Foo> invokeProvider() {
        return webClient
                .get()
                .uri("/foo")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Foo.class);
    }

}
