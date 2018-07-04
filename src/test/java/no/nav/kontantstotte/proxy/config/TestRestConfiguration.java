package no.nav.kontantstotte.proxy.config;

import no.finn.unleash.Unleash;
import no.nav.security.oidc.test.support.jersey.TestTokenGeneratorResource;

public class TestRestConfiguration extends RestConfiguration {

    public TestRestConfiguration(Unleash unleash) {
        super(unleash);
        register(TestTokenGeneratorResource.class);

    }
}
