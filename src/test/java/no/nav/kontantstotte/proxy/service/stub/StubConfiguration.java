package no.nav.kontantstotte.proxy.service.stub;

import no.nav.kontantstotte.proxy.domain.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class StubConfiguration {

    @Bean
    @Primary
    public PersonService getPersonServiceStub() {
        return new PersonServiceStub();
    }
}
