package no.nav.kontantstøtte.proxy.innsending.dokument.domain;

public class SøknadInnsendingException extends RuntimeException {

    public SøknadInnsendingException(Exception e) {
        super(e);
    }

}
