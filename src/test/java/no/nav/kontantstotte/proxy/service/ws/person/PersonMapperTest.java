package no.nav.kontantstotte.proxy.service.ws.person;

import no.nav.tjeneste.virksomhet.person.v3.informasjon.*;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


public class PersonMapperTest {

    @Test
    public void mappingAvPersonnavn() {
        String testfornavn = "Testfornavn";

        Person person = personV3(testfornavn);

        assertThat(PersonMapper.person(person).getFornavn()).isEqualTo(testfornavn);
    }

    @Test
    public void mappingAvDiskresjonskode() {
        String testDiskresjonskode = "KODE 6";
        Person person = personV3("", "", null, testDiskresjonskode, dato(LocalDate.now()));

        assertThat(PersonMapper.person(person).getDiskresjonskode()).isEqualTo(testDiskresjonskode);
    }

    public Person personV3(String fornavn, String etternavn, String fødselsnummer, String diskresjonskode, XMLGregorianCalendar fødselsdato) {
        Personnavn personnavn = new Personnavn();
        personnavn.setFornavn(fornavn);
        personnavn.setEtternavn(etternavn);

        Foedselsdato fodselsdatoV3 = new Foedselsdato();
        fodselsdatoV3.setFoedselsdato(fødselsdato);

        NorskIdent norskIdent = new NorskIdent();
        norskIdent.setIdent(fødselsnummer);

        PersonIdent personIdent = new PersonIdent();
        personIdent.setIdent(norskIdent);

        Diskresjonskoder diskresjonskoder = new Diskresjonskoder();
        diskresjonskoder.setKodeverksRef(diskresjonskode);

        Person personV3 = new Person();
        personV3.setPersonnavn(personnavn);
        personV3.setFoedselsdato(fodselsdatoV3);
        personV3.setAktoer(personIdent);
        personV3.setDiskresjonskode(diskresjonskoder);

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
        return personV3(fornavn, "", null, null, dato(LocalDate.now()));
    }
}