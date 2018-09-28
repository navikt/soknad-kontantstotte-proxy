package no.nav.kontantstotte.proxy.innsending.dokument.domain;

import java.time.LocalDateTime;

public class Soknad {

    private final byte[] pdf;

    private final String fnr;

    private final LocalDateTime innsendingTimestamp;

    public Soknad(String fnr, byte[] pdf, LocalDateTime innsendingTimestamp) {
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

    public LocalDateTime getInnsendingTimestamp() {
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
