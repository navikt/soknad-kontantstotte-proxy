package no.nav.kontantstotte.proxy.service.ws.person;

import no.nav.kontantstotte.proxy.domain.Person;

public class PersonMapper {

    public static Person person(no.nav.tjeneste.virksomhet.person.v3.informasjon.Person personV3) {
        String diskresjonskode = personV3.getDiskresjonskode() != null ? personV3.getDiskresjonskode().getKodeverksRef() : null;

        return new Person.Builder()
                .fornavn(personV3.getPersonnavn().getFornavn())
                .diskresjonskode(diskresjonskode)
                .build();
    }
}
