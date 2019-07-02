package no.nav.kontantstotte.proxy.config;

import no.nav.security.oidc.test.support.jersey.TestTokenGeneratorResource;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Primary
@Component
@ApplicationPath("/")
public class TestRestConfiguration extends RestConfiguration {

    public TestRestConfiguration() {
        register(TestTokenGeneratorResource.class);
    }
}
