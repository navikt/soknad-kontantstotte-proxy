package no.nav.kontantstotte.proxy.domain;

import no.nav.kontantstotte.proxy.service.ServiceException;

public interface PersonService {
    public Person hentPersonInfo(String fnr) throws ServiceException;

    void ping();
}
