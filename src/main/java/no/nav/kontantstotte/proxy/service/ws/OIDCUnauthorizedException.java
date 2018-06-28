package no.nav.kontantstotte.proxy.service.ws;

public class OIDCUnauthorizedException extends RuntimeException {
    public OIDCUnauthorizedException(String msg) {
        super(msg);
    }
}
