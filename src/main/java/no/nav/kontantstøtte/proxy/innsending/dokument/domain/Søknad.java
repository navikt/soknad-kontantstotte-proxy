package no.nav.kontantstøtte.proxy.innsending.dokument.domain;

public class Søknad {

    private final byte[] pdf;

    private final String fnr;

    public Søknad(String fnr, byte[] pdf) {
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
        return "Søknad{" +
                "fnr='" + fnr + '\'' +
                ", pdf=" + (pdf == null ? null : pdf.length + " bytes") +
                '}';
    }
}
