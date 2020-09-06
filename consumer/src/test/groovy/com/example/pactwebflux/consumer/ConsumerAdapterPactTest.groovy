package com.example.pactwebflux.consumer

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.model.MockProviderConfig
import spock.lang.Specification

import static au.com.dius.pact.consumer.ConsumerPactBuilder.consumer
import static au.com.dius.pact.consumer.ConsumerPactRunnerKt.runConsumerTest

class ConsumerAdapterPactTest extends Specification {

    def webConfiguration = new ConsumerConfiguration()
    def mockProviderConfig = MockProviderConfig.createDefault()

    def "retrieves foo data"() {
        given:
        def pact = consumer("consumerService")
                .hasPactWith("providerEndpoints")
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
        def result = runConsumerTest(pact, mockProviderConfig) { mockServer, context ->
            def webClient = webConfiguration.webClient(mockServer.getUrl())
            def consumerAdapter = new ConsumerAdapter(webClient)

            def foos = consumerAdapter.invokeProvider()
                .collectList().block()

            assert foos.size() == 2

            assert foos[0].id == 1l
            assert foos[0].name == 'Foo'

            assert foos[1].id == 2l
            assert foos[1].name == 'Bar'
        }

        then:
        result == new PactVerificationResult.Ok()
    }

}
