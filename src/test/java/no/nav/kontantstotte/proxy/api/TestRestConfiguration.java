package no.nav.kontantstotte.proxy.api;

import no.nav.kontantstotte.proxy.config.RestConfiguration;
import no.nav.security.oidc.test.support.jersey.TestTokenGeneratorResource;

public class TestRestConfiguration extends RestConfiguration {

    public TestRestConfiguration() {

        register(TestTokenGeneratorResource.class);

    }

}
