package no.nav.kontantstotte.proxy.oppslag.person.service.rest;

import no.nav.kontantstotte.proxy.oppslag.person.domain.Person;
import no.nav.tps.person.AdresseinfoDto;
import no.nav.tps.person.PersoninfoDto;

import java.util.Optional;
import java.util.function.Function;

import static no.nav.kontantstotte.proxy.oppslag.person.service.rest.AdresseConverter.boadresseDtoToBoadresse;

class PersonConverter {

    static Function<PersoninfoDto, Person> personinfoDtoToPerson = dto ->
        new Person.Builder()
                .fornavn(dto.getNavn().getFornavn())
                .mellomnavn(dto.getNavn().getMellomnavn())
                .slektsnavn(dto.getNavn().getSlektsnavn())
                .boadresse(Optional.ofNullable(dto.getAdresseinfo())
                        .map(AdresseinfoDto::getBoadresse)
                        .map(boadresseDtoToBoadresse)
                        .orElse(null))
                .diskresjonskode(dto.getSpesreg().getKode())
                .build();


}
