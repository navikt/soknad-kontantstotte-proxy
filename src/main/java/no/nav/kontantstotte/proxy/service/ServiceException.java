package no.nav.kontantstotte.proxy.service;

public class ServiceException extends Throwable {
    public ServiceException(Exception e) {
        super(e);
    }
}
