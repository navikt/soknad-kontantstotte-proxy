package no.nav.kontantstøtte.proxy.api;

import no.nav.kontantstøtte.proxy.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class Launcher {

    public static void main(String... args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }


}
