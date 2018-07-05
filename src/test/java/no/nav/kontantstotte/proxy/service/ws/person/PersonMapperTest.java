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


public class PersonMapperTest {


    private String testfornavn = "Testfornavn";


    public Person personV3(String fornavn) {
        Person personV3 = new Person();
        Personnavn personnavn = new Personnavn();

        personnavn.setFornavn(fornavn);
        personV3.setPersonnavn(personnavn);

        return personV3;
    }



    @Test
    public void mappingAvPersonnavn() {
        Person person = personV3(testfornavn);

        assertThat(PersonMapper.person(person).getFornavn()).isEqualTo(testfornavn);
    }

    @Test
    public void mappingAvBarn() {
        String fornavn = "Barne-fornavn";
        Familierelasjon barn = nyttBarn(fornavn);

        String ektefellenavn = "Eketefelle-fornavn";
        Familierelasjon ektefelle = nyEktefelle( ektefellenavn );


        List<Barn> barnList = PersonMapper.barn(asList(barn, ektefelle));
        assertThat(barnList).extracting("fornavn")
                .contains(fornavn)
                .doesNotContain(ektefellenavn);

    }

    private Familierelasjon nyttBarn(String fornavn) {
        return nyFamilierelasjon(fornavn, PersonMapper.BARN);
    }

    private Familierelasjon nyEktefelle(String fornavn) {
        return nyFamilierelasjon(fornavn, "EKTE");
    }

    private Familierelasjon nyFamilierelasjon(String fornavn, String relasjonstype) {
        Familierelasjoner relasjon = new Familierelasjoner();
        relasjon.setKodeverksRef(relasjonstype);

        Familierelasjon personMedRelasjon = new Familierelasjon();
        personMedRelasjon.setTilRolle(relasjon);
        personMedRelasjon.setTilPerson(personV3(fornavn));

        return personMedRelasjon;
    }
}