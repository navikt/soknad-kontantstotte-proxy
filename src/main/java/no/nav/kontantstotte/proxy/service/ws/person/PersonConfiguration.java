package no.nav.kontantstotte.proxy.service.ws.person;

import no.nav.kontantstotte.proxy.domain.PersonService;
import no.nav.kontantstotte.proxy.service.ws.WsClient;
import no.nav.tjeneste.virksomhet.person.v3.binding.PersonV3;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfiguration extends WsClient<PersonV3> {


    @Bean
    public PersonV3 healthIndicatorPerson(@Value("${VIRKSOMHET_PERSON_V3_ENDPOINTURL}") String serviceUrl) {
        return createPortForHealthIndicator(serviceUrl, PersonV3.class);
    }

    @Bean
    public PersonService personServiceTpsWs(
            @Qualifier("healthIndicatorPerson") PersonV3 healthIndicator
    ) {
        return new PersonServiceTpsWs(healthIndicator);
    }
}
