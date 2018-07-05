package no.nav.kontantstotte.proxy.service.ws.person;


import no.nav.kontantstotte.proxy.domain.Barn;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.Familierelasjon;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.Familierelasjoner;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.Person;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.Personnavn;
import org.junit.Test;

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
        Familierelasjon barn = nyttBarn(fornavn, etternavn);

        String ektefelleFornavn = "Ektefelle-fornavn";
        String ektefelleEtternavn = "Ektefelle-etternavn";
        Familierelasjon ektefelle = nyEktefelle( ektefelleFornavn, ektefelleEtternavn );


        List<Barn> barnList = PersonMapper.barn(asList(barn, ektefelle));
        assertThat(barnList).extracting("fornavn", "etternavn")
                .contains(tuple(fornavn, etternavn))
                .doesNotContain(tuple(ektefelleFornavn, ektefelleEtternavn));

    }

    private Familierelasjon nyttBarn(String fornavn, String etternavn) {
        return nyFamilierelasjon(fornavn, etternavn, PersonMapper.BARN);
    }

    private Familierelasjon nyEktefelle(String fornavn, String ektefelleEtternavn) {
        return nyFamilierelasjon(fornavn, ektefelleEtternavn, "EKTE");
    }

    private Familierelasjon nyFamilierelasjon(String fornavn, String etternavn, String relasjonstype) {
        Familierelasjoner relasjon = new Familierelasjoner();
        relasjon.setKodeverksRef(relasjonstype);

        Familierelasjon personMedRelasjon = new Familierelasjon();
        personMedRelasjon.setTilRolle(relasjon);
        personMedRelasjon.setTilPerson(personV3(fornavn, etternavn));

        return personMedRelasjon;
    }

    public Person personV3(String fornavn, String etternavn) {
        Person personV3 = new Person();
        Personnavn personnavn = new Personnavn();

        personnavn.setFornavn(fornavn);
        personnavn.setEtternavn(etternavn);
        personV3.setPersonnavn(personnavn);

        return personV3;
    }

    public Person personV3(String fornavn) {
        return personV3(fornavn, "");
    }
}