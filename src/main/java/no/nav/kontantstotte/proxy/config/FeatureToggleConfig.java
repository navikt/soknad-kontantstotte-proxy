package no.nav.kontantstotte.proxy.config;

import no.finn.unleash.DefaultUnleash;
import no.finn.unleash.Unleash;
import no.finn.unleash.util.UnleashConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureToggleConfig {

    @Bean
    public Unleash unleash(@Value("${APP_NAME}") String appName) {
        UnleashConfig config = UnleashConfig.builder()
                .appName(appName)
                .instanceId("instance x")
                .unleashAPI("http://unleash.herokuapp.com/api/")
                .build();

        return new DefaultUnleash(config);
    }

}
