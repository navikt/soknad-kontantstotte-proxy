package no.nav.kontantstotte.proxy.oppslag.person.domain;

public class SikkerhetsbegrensningException extends RuntimeException {
    public SikkerhetsbegrensningException(String msg) {
        super(msg);
    }
}
