package no.nav.kontantstøtte.proxy.oppslag.person.domain;

public class SikkerhetsbegrensningExeption extends RuntimeException {
    public SikkerhetsbegrensningExeption(String msg) {
        super(msg);
    }
}
