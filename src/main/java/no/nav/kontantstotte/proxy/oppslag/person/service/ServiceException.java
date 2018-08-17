package no.nav.kontantstotte.proxy.oppslag.person.service;

public class ServiceException extends Throwable {
    public ServiceException(Exception e) {
        super(e);
    }
}
