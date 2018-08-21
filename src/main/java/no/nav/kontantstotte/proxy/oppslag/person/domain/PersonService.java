package no.nav.kontantstotte.proxy.oppslag.person.domain;

public interface PersonService {
    Person hentPersonInfo(String fnr) throws PersonServiceException;

    void ping();
}
