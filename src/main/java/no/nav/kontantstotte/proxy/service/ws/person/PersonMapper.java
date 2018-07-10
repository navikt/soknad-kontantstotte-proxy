package no.nav.kontantstotte.proxy.service.ws.person;

import no.nav.kontantstotte.proxy.domain.Person;

public class PersonMapper {

    public static Person person(no.nav.tjeneste.virksomhet.person.v3.informasjon.Person personV3) {
        return new Person.Builder()
                .fornavn(personV3.getPersonnavn().getFornavn())
                .build();
    }
}
