package no.nav.kontantstotte.proxy.oppslag.person.service.ws;

public class SikkerhetsbegrensningExeption extends RuntimeException {
    public SikkerhetsbegrensningExeption(String msg) {
        super(msg);
    }
}
