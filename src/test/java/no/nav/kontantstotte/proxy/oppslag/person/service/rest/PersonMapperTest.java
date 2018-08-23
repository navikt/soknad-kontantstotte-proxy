package no.nav.kontantstotte.proxy.oppslag.person.service.rest;

import no.nav.kontantstotte.proxy.oppslag.person.domain.SikkerhetsbegrensningExeption;
import no.nav.tps.person.NavnDto;
import no.nav.tps.person.PersoninfoDto;
import no.nav.tps.person.SpesregDto;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static no.nav.kontantstotte.proxy.oppslag.person.service.rest.PersonConverter.personinfoDtoToPerson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;


public class PersonMapperTest {

    @Rule
    public ExpectedException exception = none();

    @Test
    public void mappingAvPersonnavn() {

        PersoninfoDto tpsPerson = tpsPerson("Testfornavn", "Etternanvsen", "12345678901", null);

        assertThat(personinfoDtoToPerson.apply(tpsPerson).getFornavn()).isEqualTo("Testfornavn");
        assertThat(personinfoDtoToPerson.apply(tpsPerson).getSlektsnavn()).isEqualTo("Etternanvsen");
    }

    @Test
    public void mappingAvDiskresjonskode() {
        exception.expect(SikkerhetsbegrensningExeption.class);
        
        personinfoDtoToPerson.apply(tpsPerson("", "", null, "KODE 6"));
    }

    private PersoninfoDto tpsPerson(String fornavn, String etternavn, String fodselsnummer, String diskresjonskode) {

        return new PersoninfoDto()
                .navn(new NavnDto()
                        .fornavn(fornavn)
                        .slektsnavn(etternavn))
                .ident(fodselsnummer)
                .spesreg(new SpesregDto()
                        .kode(diskresjonskode));

    }

}