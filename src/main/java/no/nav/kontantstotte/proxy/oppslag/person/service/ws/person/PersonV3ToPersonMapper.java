package no.nav.kontantstotte.proxy.oppslag.person.service.ws.person;

import no.nav.kontantstotte.proxy.oppslag.person.domain.Person;

public class PersonV3ToPersonMapper {

    public static Person map(no.nav.tjeneste.virksomhet.person.v3.informasjon.Person personV3) {
        return new Person.Builder()
                .fornavn(personV3.getPersonnavn().getFornavn())
                .build();
    }
}
