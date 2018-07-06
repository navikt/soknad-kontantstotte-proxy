package no.nav.kontantstotte.proxy.service.ws.person;

import no.nav.kontantstotte.proxy.domain.Barn;
import no.nav.kontantstotte.proxy.domain.Person;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.Familierelasjon;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.PersonIdent;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
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
                .filter(erIkkeDød())
                .map(person -> new Barn.Builder()
                        .fornavn(person.getPersonnavn().getFornavn())
                        .etternavn(person.getPersonnavn().getEtternavn())
                        .fødselsnummer(mapFødselsnummer(person))
                        .fødselsdato(mapFødselsdato(person))
                        .build()
                )
                .collect(Collectors.toList());
    }

    private static Predicate<no.nav.tjeneste.virksomhet.person.v3.informasjon.Person> erIkkeDød() {
        return erDød().negate();
    }

    private static Predicate<no.nav.tjeneste.virksomhet.person.v3.informasjon.Person> erDød() {
        return p -> p.getPersonstatus() != null && Arrays.asList("DØD", "DØDD").contains(p.getPersonstatus().getPersonstatus().getKodeverksRef());
    }

    private static LocalDate mapFødselsdato(no.nav.tjeneste.virksomhet.person.v3.informasjon.Person person) {
        return toLocalDate(person.getFoedselsdato().getFoedselsdato());
    }

    private static String mapFødselsnummer(no.nav.tjeneste.virksomhet.person.v3.informasjon.Person person) {
        return PersonIdent.class.cast(person.getAktoer()).getIdent().getIdent();
    }

    private static LocalDate toLocalDate(XMLGregorianCalendar cal) {
        return LocalDate.of(cal.getYear(), cal.getMonth(), cal.getDay());
    }
}
