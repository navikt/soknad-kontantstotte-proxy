package no.nav.kontantstotte.proxy.service;

public class ServiceException extends RuntimeException {
    public ServiceException(Exception e) {
        super(e);
    }
}
