package no.nav.kontantstotte.proxy.service.stub;

import no.nav.kontantstotte.proxy.domain.Barn;
import no.nav.kontantstotte.proxy.domain.Person;
import no.nav.kontantstotte.proxy.domain.PersonService;

import java.time.LocalDate;
import java.util.Arrays;

public class PersonServiceStub implements PersonService {

    @Override
    public Person hentPersonInfo(String fnr) {
        Person person = new Person.Builder()
                .fornavn("Mock")
                .barn(Arrays.asList(
                        new Barn.Builder()
                                .fornavn("Mockbarn")
                                .etternavn("Mockesen")
                                .fødselsdato(LocalDate.of(2018, 1, 25))
                                .build(),
                        new Barn.Builder()
                                .fornavn("Mickbarn")
                                .etternavn("Mockesen")
                                .fødselsdato(LocalDate.of(2017, 5, 12))
                                .build())
                )
                .build();
        return person;
    }

    @Override
    public void ping() {

    }
}
