package no.nav.kontantstotte.proxy.innsending.dokument.domain;

import java.time.Instant;

public class Soknad {

    private final byte[] pdf;

    private final String fnr;

    private final Instant innsendingTimestamp;

    public Soknad(String fnr, byte[] pdf, Instant innsendingTimestamp) {
        this.pdf = pdf;
        this.fnr = fnr;
        this.innsendingTimestamp = innsendingTimestamp;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public String getFnr() {
        return fnr;
    }

    public Instant getInnsendingTimestamp() {
        return innsendingTimestamp;
    }

    @Override
    public String toString() {
        return "Soknad{" +
                "fnr='" + fnr + '\'' +
                ", pdf=" + (pdf == null ? null : pdf.length + " bytes") +
                '}';
    }
}
