package no.nav.kontantstotte.proxy.person.domain;

import no.nav.kontantstotte.proxy.person.service.ServiceException;

public interface PersonService {
    public Person hentPersonInfo(String fnr) throws ServiceException;

    void ping();
}
