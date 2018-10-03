package no.nav.kontantstotte.proxy.innsending.dokument.domain;

import java.time.Instant;

public class Soknad {

    private final byte[] pdf;

    private final String fnr;

    private final Instant innsendingsTidspunkt;

    public Soknad(String fnr, byte[] pdf, Instant innsendingsTidspunkt) {
        this.pdf = pdf;
        this.fnr = fnr;
        this.innsendingsTidspunkt = innsendingsTidspunkt;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public String getFnr() {
        return fnr;
    }

    public Instant getInnsendingsTidspunkt() {
        return innsendingsTidspunkt;
    }

    @Override
    public String toString() {
        return "Soknad{" +
                "fnr='" + fnr + '\'' +
                ", pdf=" + (pdf == null ? null : pdf.length + " bytes") +
                '}';
    }
}
