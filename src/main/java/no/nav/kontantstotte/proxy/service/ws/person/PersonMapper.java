package no.nav.kontantstotte.proxy.service.ws.person;

import no.nav.kontantstotte.proxy.domain.Barn;
import no.nav.kontantstotte.proxy.domain.Person;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.Familierelasjon;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.PersonIdent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PersonMapper {

    private static final Logger LOG = LoggerFactory.getLogger(PersonMapper.class);

    public static final String BARN = "BARN";

    public static Person person(no.nav.tjeneste.virksomhet.person.v3.informasjon.Person personV3) {
        return new Person.Builder()
                .fornavn(personV3.getPersonnavn().getFornavn())
                .barn(barn(personV3.getHarFraRolleI()))
                .build();
    }

    public static List<Barn> barn(List<Familierelasjon> familierelasjoner) {
        LOG.info("Pålogget bruker har {} familierelasjoner",familierelasjoner.size());
        List<Barn> list = familierelasjoner.stream()
                .filter(rel -> BARN.equals(rel.getTilRolle().getKodeverksRef()))
                .map(rel -> rel.getTilPerson())
                .peek(e -> LOG.info("Funnet barn {}", e.getPersonnavn().getFornavn()))
                .filter(erIkkeDød())
                .peek(e -> LOG.info("Funnet barn etter filtering{}", e.getPersonnavn().getFornavn()))
                .map(person -> new Barn.Builder()
                        .fornavn(person.getPersonnavn().getFornavn())
                        .etternavn(person.getPersonnavn().getEtternavn())
                        .fødselsnummer(mapFødselsnummer(person))
                        .fødselsdato(mapFødselsdato(person))
                        .build()
                )
                .collect(Collectors.toList());
        LOG.info("Pålogget bruker har {} mappede barn",list.size());
        return list;
    }

    private static Predicate<no.nav.tjeneste.virksomhet.person.v3.informasjon.Person> erIkkeDød() {
        return erDød().negate();
    }

    private static Predicate<no.nav.tjeneste.virksomhet.person.v3.informasjon.Person> erDød() {
        return harPersonstatusDød().or(harDødsdato());
    }

    private static Predicate<no.nav.tjeneste.virksomhet.person.v3.informasjon.Person> harDødsdato() {
        return person -> person.getDoedsdato() != null;
    }

    private static Predicate<no.nav.tjeneste.virksomhet.person.v3.informasjon.Person> harPersonstatusDød() {
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
