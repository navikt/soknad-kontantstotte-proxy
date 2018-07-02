package no.nav.kontantstotte.innsending.domene;

public class Soknad {

    private final byte[] pdf;

    private final String fnr;

    public Soknad(String fnr, byte[] pdf) {
        this.pdf = pdf;
        this.fnr = fnr;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public String getFnr() {
        return fnr;
    }
}
