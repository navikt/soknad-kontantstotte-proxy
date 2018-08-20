package no.nav.kontantstotte.proxy.oppslag.person.domain;

import no.nav.kontantstotte.proxy.oppslag.person.service.ServiceException;

public interface PersonService {
    Person hentPersonInfo(String fnr) throws ServiceException;

    void ping();
}
