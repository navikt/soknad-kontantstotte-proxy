package no.nav.kontantstøtte.proxy.oppslag.person.service.rest;

import no.nav.kontantstøtte.proxy.oppslag.person.domain.Person;
import no.nav.kontantstøtte.proxy.oppslag.person.domain.SikkerhetsbegrensningExeption;
import no.nav.tps.person.AdresseinfoDto;
import no.nav.tps.person.PersoninfoDto;
import no.nav.tps.person.SpesregDto;

import java.util.Optional;
import java.util.function.Function;

import static no.nav.kontantstøtte.proxy.oppslag.person.service.rest.AdresseConverter.boadresseDtoToBoadresse;

class PersonConverter {

    static Function<PersoninfoDto, Person> personinfoDtoToPerson = dto -> {

        if(Optional.ofNullable(dto.getSpesreg()).map(SpesregDto::getKode).isPresent()) {
            throw new SikkerhetsbegrensningExeption("Personen er registrert med spesreg");
        }

        return new Person.Builder()
                .fornavn(dto.getNavn().getFornavn())
                .mellomnavn(dto.getNavn().getMellomnavn())
                .slektsnavn(dto.getNavn().getSlektsnavn())
                .boadresse(Optional.ofNullable(dto.getAdresseinfo())
                        .map(AdresseinfoDto::getBoadresse)
                        .map(boadresseDtoToBoadresse)
                        .orElse(null))
                .build();
    };



}
