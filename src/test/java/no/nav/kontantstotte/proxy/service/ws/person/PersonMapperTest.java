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
        Familierelasjon barn = nyttBarn(personV3(fornavn, etternavn));

        String ektefelleFornavn = "Ektefelle-fornavn";
        String ektefelleEtternavn = "Ektefelle-etternavn";
        Familierelasjon ektefelle = nyEktefelle( personV3(ektefelleFornavn, ektefelleEtternavn));


        List<Barn> barnList = PersonMapper.barn(asList(barn, ektefelle));
        assertThat(barnList).extracting("fornavn", "etternavn")
                .contains(tuple(fornavn, etternavn))
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