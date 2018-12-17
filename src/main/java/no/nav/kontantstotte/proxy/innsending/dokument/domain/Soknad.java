package no.nav.kontantstotte.proxy.innsending.dokument.domain;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class Soknad {

    private final byte[] pdf;

    private final String fnr;

    private final Instant innsendingsTidspunkt;

    private final List<SoknadVedlegg> vedlegg;

    public Soknad(String fnr, byte[] pdf, Instant innsendingsTidspunkt, List<SoknadVedlegg> vedlegg) {
        this.pdf = pdf;
        this.fnr = fnr;
        this.innsendingsTidspunkt = innsendingsTidspunkt;
        this.vedlegg = vedlegg == null ? Collections.emptyList() : vedlegg;
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

    public List<SoknadVedlegg> getVedlegg() {
        return vedlegg;
    }

    @Override
    public String toString() {
        return "Soknad{" +
                "fnr='" + fnr + '\'' +
                ", pdf=" + (pdf == null ? null : pdf.length + " bytes") +
                '}';
    }
}
