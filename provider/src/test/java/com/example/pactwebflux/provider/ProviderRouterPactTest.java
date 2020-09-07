package com.example.pactwebflux.provider;

import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.junitsupport.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.WebFluxTarget;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@RunWith(SpringRestPactRunner.class)
@Provider("providerEndpoints")
@PactFolder("pacts")
@Import({ProviderRouter.class, ProviderHandler.class})
public class ProviderRouterPactTest {

    @TestTarget
    public WebFluxTarget target = new WebFluxTarget();

    @Autowired
    public RouterFunction<ServerResponse> routerFunction;

    @Before
    public void setup() {
        target.setRouterFunction(routerFunction);
    }

}
