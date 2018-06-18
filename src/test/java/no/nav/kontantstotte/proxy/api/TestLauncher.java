package no.nav.kontantstotte.proxy.api;

import no.nav.kontantstotte.proxy.config.ApplicationConfig;
import no.nav.security.spring.oidc.test.TokenGeneratorConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan({"no.nav.kontantstotte.proxy.api"})
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
@Import(TokenGeneratorConfiguration.class)
public class TestLauncher {

    public static void main(String... args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }

}
