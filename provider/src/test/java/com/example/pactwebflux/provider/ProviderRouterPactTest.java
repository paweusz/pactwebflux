package com.example.pactwebflux.provider;

import au.com.dius.pact.provider.junit.RestPactRunner;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.junitsupport.target.TestTarget;
import au.com.dius.pact.provider.spring.target.WebFluxTarget;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@RunWith(RestPactRunner.class)
@Provider("providerService")
@PactFolder("pacts")
public class ProviderRouterPactTest {

    @TestTarget
    public WebFluxTarget target = new WebFluxTarget();

    private ProviderHandler handler = new ProviderHandler();
    private RouterFunction<ServerResponse> routerFunction
            = new ProviderRouter(handler).routes();

    @Before
    public void setup() {
        target.setRouterFunction(routerFunction);
    }

}
