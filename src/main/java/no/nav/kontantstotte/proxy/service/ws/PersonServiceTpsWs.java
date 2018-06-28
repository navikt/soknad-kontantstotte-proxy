package no.nav.kontantstotte.proxy.service.ws;

import no.nav.kontantstotte.proxy.domain.Person;
import no.nav.kontantstotte.proxy.domain.PersonService;
import no.nav.tjeneste.virksomhet.person.v3.binding.PersonV3;

public class PersonServiceTpsWs implements PersonService {

    private final PersonV3 personV3;

    public PersonServiceTpsWs(PersonV3 personV3) {
        this.personV3 = personV3;
    }

    @Override
    public Person hentPersonInfo(String fnr) {
        return null;
    }
}
