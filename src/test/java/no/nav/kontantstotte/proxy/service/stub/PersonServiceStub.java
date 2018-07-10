package no.nav.kontantstotte.proxy.service.stub;

import no.nav.kontantstotte.proxy.domain.Person;
import no.nav.kontantstotte.proxy.domain.PersonService;


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
