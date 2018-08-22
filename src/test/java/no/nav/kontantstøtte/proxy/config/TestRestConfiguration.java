package no.nav.kontantstøtte.proxy.config;

import no.nav.security.oidc.test.support.jersey.TestTokenGeneratorResource;

public class TestRestConfiguration extends RestConfiguration {

    public TestRestConfiguration() {
        register(TestTokenGeneratorResource.class);

    }
}
