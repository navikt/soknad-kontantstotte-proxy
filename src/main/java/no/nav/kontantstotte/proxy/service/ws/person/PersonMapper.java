package no.nav.kontantstotte.proxy.service.ws.person;

import no.nav.kontantstotte.proxy.domain.Barn;
import no.nav.kontantstotte.proxy.domain.Person;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.Familierelasjon;

import java.util.List;
import java.util.stream.Collectors;

public class PersonMapper {

    public static final String BARN = "BARN";

    public static Person person(no.nav.tjeneste.virksomhet.person.v3.informasjon.Person personV3) {
        return new Person.Builder()
                .fornavn(personV3.getPersonnavn().getFornavn())
                .barn(barn(personV3.getHarFraRolleI()))
                .build();
    }

    public static List<Barn> barn(List<Familierelasjon> familierelasjoner) {
        return familierelasjoner.stream()
                .filter(rel -> BARN.equals(rel.getTilRolle().getKodeverksRef()))
                .map(rel -> rel.getTilPerson())
                .map(person -> new Barn.Builder()
                        .fornavn(person.getPersonnavn().getFornavn())
                        .build()
                )
                .collect(Collectors.toList());
    }


}
