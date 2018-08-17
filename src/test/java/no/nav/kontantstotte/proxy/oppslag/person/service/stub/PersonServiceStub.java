package no.nav.kontantstotte.proxy.oppslag.person.service.stub;

import no.nav.kontantstotte.proxy.oppslag.person.domain.Person;
import no.nav.kontantstotte.proxy.oppslag.person.domain.PersonService;


public class PersonServiceStub implements PersonService {

    @Override
    public Person hentPersonInfo(String fnr) {
        Person person = new Person.Builder()
                .fornavn("Mock")
                .build();
        return person;
    }

    @Override
    public void ping() {

    }
}
