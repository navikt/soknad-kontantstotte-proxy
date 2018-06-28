package no.nav.kontantstotte.proxy.service.ws;

import no.nav.kontantstotte.proxy.domain.PersonService;
import no.nav.tjeneste.virksomhet.person.v3.binding.PersonV3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfiguration extends WsClient<PersonV3> {

    @Bean
    public PersonV3 personV3() {
//        return createPort()
    }

    @Bean
    public PersonService personServiceTpsWs() {
        return new PersonServiceTpsWs(null);
    }
}
