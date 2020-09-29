package com.example.pactwebflux.consumer

import au.com.dius.pact.consumer.ConsumerPactBuilder
import au.com.dius.pact.consumer.ConsumerPactRunnerKt
import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.model.MockProviderConfig
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier
import spock.lang.Specification

class ConsumerAdapterPactTest extends Specification {

    def "retrieves foo data"() {
        given:
        def pact = ConsumerPactBuilder.consumer("consumerService")
            .hasPactWith("providerService")
            .uponReceiving("sample request")
            .method("GET")
            .path("/foo")
            .willRespondWith()
            .status(200)
            .headers(["Content-Type": "application/json"])
            .body("""
                    [
                        {"id": 1, "name": "Foo"},
                        {"id": 2, "name": "Bar"}
                    ]
                """.stripIndent())
            .toPact()

        when:
        def result = ConsumerPactRunnerKt.runConsumerTest(
            pact, MockProviderConfig.createDefault()) { mockServer, context ->

            def webClient = WebClient.create(mockServer.getUrl())
            def consumerAdapter = new ConsumerAdapter(webClient)

            def resultFlux = consumerAdapter.invokeProvider()

            StepVerifier.create(resultFlux)
                .expectNext(new Foo(1l, 'Foo'))
                .expectNext(new Foo(2l, 'Bar'))
                .verifyComplete()
        }

        then:
        result instanceof PactVerificationResult.Ok
    }

}
