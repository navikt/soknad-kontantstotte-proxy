package no.nav.kontantstotte.proxy.person.service;

public class ServiceException extends Throwable {
    public ServiceException(Exception e) {
        super(e);
    }
}
