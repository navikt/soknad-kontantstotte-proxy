package no.nav.kontantstøtte.proxy.oppslag.person.service.rest;

import no.nav.kontantstøtte.proxy.oppslag.person.domain.PersonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class PersonRestConfiguration {

    @Bean
    public PersonService personServiceTpsWs(@Value("${PERSONINFO_V1_URL}") URI personServiceUri) {
        return new PersonServiceClient(personServiceUri);
    }

    @Bean
    public PersonRestHealthIndicator tpsPersonHealthIndicator(PersonService personService) {
        return new PersonRestHealthIndicator(personService);
    }
}
