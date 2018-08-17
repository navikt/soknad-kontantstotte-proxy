package no.nav.kontantstotte.proxy.oppslag.person.service.ws.person;

import no.nav.kontantstotte.proxy.oppslag.person.domain.PersonService;
import no.nav.kontantstotte.proxy.oppslag.person.service.ws.WsClient;
import no.nav.tjeneste.virksomhet.person.v3.binding.PersonV3;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfiguration extends WsClient<PersonV3> {

    @Bean
    public PersonV3 personV3(@Value("${VIRKSOMHET_PERSON_V3_ENDPOINTURL}") String serviceUrl) {
        return createPort(serviceUrl, PersonV3.class);
    }

    @Bean
    public PersonV3 healthIndicatorPerson(@Value("${VIRKSOMHET_PERSON_V3_ENDPOINTURL}") String serviceUrl) {
        return createPortForHealthIndicator(serviceUrl, PersonV3.class);
    }

    @Bean
    public PersonService personServiceTpsWs(
            @Qualifier("personV3") PersonV3 personV3,
            @Qualifier("healthIndicatorPerson") PersonV3 healthIndicator
    ) {
        return new PersonServiceTpsWs(personV3, healthIndicator);
    }
}
