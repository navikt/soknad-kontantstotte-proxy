package no.nav.kontantstotte.innsending.domene;

import java.util.Arrays;

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

    @Override
    public String toString() {
        return "Soknad{" +
                "fnr='" + fnr + '\'' +
                ", pdf=" + (pdf == null ? null : pdf.length + " bytes") +
                '}';
    }
}
