package no.nav.kontantstotte.proxy.api;

import no.finn.unleash.FakeUnleash;
import no.finn.unleash.Unleash;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadSender;
import no.nav.kontantstotte.proxy.config.ApplicationConfig;
import no.nav.kontantstotte.proxy.config.TestRestConfiguration;
import no.nav.security.oidc.configuration.OIDCResourceRetriever;
import no.nav.security.oidc.test.support.FileResourceRetriever;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import static no.nav.kontantstotte.proxy.api.rest.oppslag.PersonResource.BRUK_MOCK_TPS_INTEGRASJON;
import static no.nav.kontantstotte.proxy.api.rest.oppslag.PersonResource.BRUK_TPS_INTEGRASJON;


@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@Import(ApplicationConfig.class)
public class TestLauncher {

    public static void main(String... args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }

    /**
     * To be able to ovverride the oidc validation properties in
     * EnableOIDCTokenValidationConfiguration in oidc-spring-support
     */
    @Bean
    @Primary
    OIDCResourceRetriever overrideOidcResourceRetriever() {
        return new FileResourceRetriever("/metadata.json", "/jwkset.json");
    }

    @Bean
    Unleash fakeUnleash() {
        FakeUnleash unleash = new FakeUnleash();
        //unleash.enableAll();
        unleash.enable( BRUK_MOCK_TPS_INTEGRASJON );
        return unleash;
    }

    @Bean
    @Primary
    public ResourceConfig proxyConfig() {
        return new TestRestConfiguration();
    }

    @Bean
    public SoknadSender soknadSender() {
        return input -> {};
    }

}
