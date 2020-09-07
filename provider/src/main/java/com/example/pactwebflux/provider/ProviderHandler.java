package com.example.pactwebflux.provider;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
class ProviderHandler {

    Mono<ServerResponse> getFoo(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(Flux.just(
                        new Foo(1l, "Foo"),
                        new Foo(2l, "Bar")
                ), Foo.class);
    }

}
