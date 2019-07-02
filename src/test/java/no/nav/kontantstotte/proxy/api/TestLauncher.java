package no.nav.kontantstotte.proxy.api;

import no.finn.unleash.FakeUnleash;
import no.finn.unleash.Unleash;
import no.nav.kontantstotte.proxy.config.ApplicationConfig;
import no.nav.kontantstotte.proxy.config.TestRestConfiguration;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadSender;
import no.nav.security.oidc.configuration.OIDCResourceRetriever;
import no.nav.security.oidc.test.support.FileResourceRetriever;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import static no.nav.kontantstotte.proxy.api.rest.mottak.SoknadMottakController.BRUK_DOKMOT_INTEGRASJON;


@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@Import(ApplicationConfig.class)
public class TestLauncher {

    public static void main(String... args) {
        new SpringApplicationBuilder(ApplicationConfig.class)
                .web(WebApplicationType.SERVLET)
                .build()
                .run(args);
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
    @Primary
    public ResourceConfig proxyConfig() {
        return new TestRestConfiguration();
    }

    @Bean
    Unleash fakeUnleash() {
        FakeUnleash unleash = new FakeUnleash();
        unleash.enable( BRUK_DOKMOT_INTEGRASJON );
        return unleash;
    }

    @Bean
    public SoknadSender soknadSender() {
        return input -> {};
    }

}
