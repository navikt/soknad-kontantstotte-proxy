package no.nav.kontantstotte.proxy.service.ws.person;


import no.nav.kontantstotte.proxy.domain.Barn;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.*;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;


public class PersonMapperTest {

    @Test
    public void mappingAvPersonnavn() {
        String testfornavn = "Testfornavn";

        Person person = personV3(testfornavn);

        assertThat(PersonMapper.person(person).getFornavn()).isEqualTo(testfornavn);
    }

    @Test
    public void mappingAvBarn() {
        String fornavn = "Barne-fornavn";
        String etternavn = "Barne-etternavn";
        String fødselsnummer = "250118xxxxx";
        LocalDate fødselsdato = LocalDate.of(2018, 1, 25);
        Familierelasjon barn = nyttBarn(personV3(fornavn, etternavn, fødselsnummer, dato(fødselsdato)));

        String ektefelleFornavn = "Ektefelle-fornavn";
        String ektefelleEtternavn = "Ektefelle-etternavn";
        Familierelasjon ektefelle = nyEktefelle( personV3(ektefelleFornavn, ektefelleEtternavn));


        List<Barn> barnList = PersonMapper.barn(asList(barn, ektefelle));
        assertThat(barnList).extracting("fornavn", "etternavn", "fødselsnummer", "fødselsdato")
                .contains(tuple(fornavn, etternavn, fødselsnummer, fødselsdato))
                .doesNotContain(tuple(ektefelleFornavn, ektefelleEtternavn));

    }

    @Test
    public void filtrerPersonstatuserForDød() {
        Person dødPerson = personV3("Mock");
        dødPerson.setPersonstatus(personstatus("DØD"));

        Person døddPerson = personV3("Mack");
        døddPerson.setPersonstatus(personstatus("DØDD"));

        Person aktivPerson = personV3("Mick");
        aktivPerson.setPersonstatus(personstatus("AKTV"));

        List<Barn> barnList = PersonMapper.barn(asList(nyttBarn(aktivPerson), nyttBarn(dødPerson), nyttBarn(døddPerson)));

        assertThat(barnList).hasSize(1).extracting("fornavn").contains("Mick").doesNotContain("Mock", "Mack");
    }

    @Test
    public void filtrerPersonerMedDødsdato() {
        Person personMedDødsdato = personV3("Mock");
        Doedsdato dødsdato = new Doedsdato();
        dødsdato.setDoedsdato(dato(LocalDate.of(2018, 4,1)));
        personMedDødsdato.setDoedsdato(dødsdato);

        Person aktivPerson = personV3("Mick");

        List<Barn> barnList = PersonMapper.barn(asList(nyttBarn(aktivPerson), nyttBarn(personMedDødsdato)));

        assertThat(barnList).hasSize(1).extracting("fornavn").contains("Mick").doesNotContain("Mock");
    }

    private Personstatus personstatus(String status) {
        Personstatuser statusDød = new Personstatuser();
        statusDød.setKodeverksRef(status);

        Personstatus personstatus = new Personstatus();
        personstatus.setPersonstatus(statusDød);
        return personstatus;
    }

    private Familierelasjon nyttBarn(Person person) {
        return nyttFamiliemedlem(relasjonstype(PersonMapper.BARN), person);
    }

    private Familierelasjon nyEktefelle(Person person) {
        return nyttFamiliemedlem(relasjonstype("EKTE"), person);
    }

    private Familierelasjon nyttFamiliemedlem(Familierelasjoner relasjonstype, Person person) {

        Familierelasjon personMedRelasjon = new Familierelasjon();
        personMedRelasjon.setTilRolle(relasjonstype);
        personMedRelasjon.setTilPerson(person);

        return personMedRelasjon;
    }

    private Familierelasjoner relasjonstype(String relasjonskode) {
        Familierelasjoner relasjon = new Familierelasjoner();
        relasjon.setValue(relasjonskode);
        return relasjon;
    }

    private Person personV3(String ektefelleFornavn, String ektefelleEtternavn) {
        return personV3(ektefelleFornavn, ektefelleEtternavn, null, null);
    }

    public Person personV3(String fornavn, String etternavn, String fødselsnummer, XMLGregorianCalendar fødselsdato) {
        Personnavn personnavn = new Personnavn();
        personnavn.setFornavn(fornavn);
        personnavn.setEtternavn(etternavn);

        Foedselsdato fodselsdatoV3 = new Foedselsdato();
        fodselsdatoV3.setFoedselsdato(fødselsdato);

        NorskIdent norskIdent = new NorskIdent();
        norskIdent.setIdent(fødselsnummer);

        PersonIdent personIdent = new PersonIdent();
        personIdent.setIdent(norskIdent);

        Person personV3 = new Person();
        personV3.setPersonnavn(personnavn);
        personV3.setFoedselsdato(fodselsdatoV3);
        personV3.setAktoer(personIdent);

        return personV3;
    }

    private XMLGregorianCalendar dato(LocalDate dato) {
        XMLGregorianCalendar xgc = null;
        try {
            xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        } catch (DatatypeConfigurationException e) {
            Assert.fail();
        }
        xgc.setYear(dato.getYear());
        xgc.setMonth(dato.getMonthValue());
        xgc.setDay(dato.getDayOfMonth());
        return xgc;
    }

    public Person personV3(String fornavn) {
        return personV3(fornavn, "", null, dato(LocalDate.now()));
    }
}