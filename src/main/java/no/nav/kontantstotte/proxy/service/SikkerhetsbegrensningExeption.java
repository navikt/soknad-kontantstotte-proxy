package no.nav.kontantstotte.proxy.service;

public class SikkerhetsbegrensningExeption extends RuntimeException {
    public SikkerhetsbegrensningExeption(String msg) {
        super(msg);
    }
}
