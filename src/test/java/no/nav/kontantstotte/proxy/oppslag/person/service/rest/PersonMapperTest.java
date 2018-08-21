package no.nav.kontantstotte.proxy.oppslag.person.service.rest;

import no.nav.tps.person.NavnDto;
import no.nav.tps.person.PersoninfoDto;
import no.nav.tps.person.SpesregDto;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class PersonMapperTest {

    @Test
    public void mappingAvPersonnavn() {

        PersoninfoDto tpsPerson = tpsPerson("Testfornavn", "Etternanvsen", "12345678901", null);

        assertThat(PersonConverter.personinfoDtoToPerson.apply(tpsPerson).getFornavn()).isEqualTo("Testfornavn");
        assertThat(PersonConverter.personinfoDtoToPerson.apply(tpsPerson).getSlektsnavn()).isEqualTo("Etternanvsen");
    }

    @Test
    public void mappingAvDiskresjonskode() {
        PersoninfoDto tpsPerson = tpsPerson("", "", null, "KODE 6");

        assertThat(PersonConverter.personinfoDtoToPerson.apply(tpsPerson).getDiskresjonskode().isPresent())
                .isTrue();
        assertThat(PersonConverter.personinfoDtoToPerson.apply(tpsPerson).getDiskresjonskode().get())
                .isEqualTo("KODE 6");
    }

    private PersoninfoDto tpsPerson(String fornavn, String etternavn, String fødselsnummer, String diskresjonskode) {

        return new PersoninfoDto()
                .navn(new NavnDto()
                        .fornavn(fornavn)
                        .slektsnavn(etternavn))
                .ident(fødselsnummer)
                .spesreg(new SpesregDto()
                        .kode(diskresjonskode));

    }

}