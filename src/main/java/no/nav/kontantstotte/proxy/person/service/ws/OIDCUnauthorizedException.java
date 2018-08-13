package no.nav.kontantstotte.proxy.person.service.ws;

public class OIDCUnauthorizedException extends RuntimeException {
    public OIDCUnauthorizedException(String msg) {
        super(msg);
    }
}
