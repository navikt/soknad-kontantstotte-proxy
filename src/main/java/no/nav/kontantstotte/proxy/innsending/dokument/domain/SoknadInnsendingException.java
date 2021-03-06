package no.nav.kontantstotte.proxy.innsending.dokument.domain;

public class SoknadInnsendingException extends RuntimeException {

    private final String config;

    public SoknadInnsendingException(Exception e, String config) {

        super(e);
        this.config = config;
    }

    public String getConfig() {
        return config;
    }
}
