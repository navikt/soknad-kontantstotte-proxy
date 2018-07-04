package no.nav.kontantstotte.proxy.config;

import no.finn.unleash.DefaultUnleash;
import no.finn.unleash.Unleash;
import no.finn.unleash.util.UnleashConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class FeatureToggleConfig {

    private static final String APP_NAME_PROPERTY_NAME = "APP_NAME";
    private static final String UNLEASH_API_URL_PROPERTY_NAME = "UNLEASH_API_URL";

    @Bean
    public Unleash unleash(
            @Value(APP_NAME_PROPERTY_NAME) String appName,
            @Value(UNLEASH_API_URL_PROPERTY_NAME) String unleasApiUrl
    ) {
        UnleashConfig config = UnleashConfig.builder()
                .appName(appName)
                .unleashAPI(unleasApiUrl)
                .build();

        return new DefaultUnleash(config);
    }

}
