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
        LocalDate fødselsdato = LocalDate.of(2018, 1, 25);
        Familierelasjon barn = nyttBarn(personV3(fornavn, etternavn, dato(fødselsdato)));

        String ektefelleFornavn = "Ektefelle-fornavn";
        String ektefelleEtternavn = "Ektefelle-etternavn";
        Familierelasjon ektefelle = nyEktefelle( personV3(ektefelleFornavn, ektefelleEtternavn));


        List<Barn> barnList = PersonMapper.barn(asList(barn, ektefelle));
        assertThat(barnList).extracting("fornavn", "etternavn", "fødselsdato")
                .contains(tuple(fornavn, etternavn, fødselsdato))
                .doesNotContain(tuple(ektefelleFornavn, ektefelleEtternavn));

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
        relasjon.setKodeverksRef(relasjonskode);
        return relasjon;
    }

    private Person personV3(String ektefelleFornavn, String ektefelleEtternavn) {
        return personV3(ektefelleFornavn, ektefelleEtternavn, null);
    }

    public Person personV3(String fornavn, String etternavn, XMLGregorianCalendar fødselsdato) {
        Person personV3 = new Person();
        Personnavn personnavn = new Personnavn();

        personnavn.setFornavn(fornavn);
        personnavn.setEtternavn(etternavn);
        personV3.setPersonnavn(personnavn);

        Foedselsdato fodselsdatoV3 = new Foedselsdato();
        fodselsdatoV3.setFoedselsdato(fødselsdato);
        personV3.setFoedselsdato(fodselsdatoV3);

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
        return personV3(fornavn, "", null);
    }
}