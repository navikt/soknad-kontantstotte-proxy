package no.nav.kontantstotte.proxy.oppslag.person.service.ws.person;

import no.nav.kontantstotte.proxy.oppslag.person.domain.Person;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.Diskresjonskoder;

import java.util.Optional;

public class PersonMapper {

    public static Person person(no.nav.tjeneste.virksomhet.person.v3.informasjon.Person personV3) {
        String diskresjonskode =  Optional.ofNullable(personV3.getDiskresjonskode())
                .map(Diskresjonskoder::getKodeverksRef).orElse(null);

        return new Person.Builder()
                .fornavn(personV3.getPersonnavn().getFornavn())
                .diskresjonskode(diskresjonskode)
                .build();
    }
}
