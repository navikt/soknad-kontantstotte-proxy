package no.nav.kontantstotte.proxy.innsending.dokument.domain;

public class SoknadVedlegg {

    private final byte[] data;
    private final String tittel;
    private final String dokumenttype;

    public SoknadVedlegg(byte[] data, String tittel, String dokumenttype) {
        this.data = data;
        this.tittel = tittel;
        this.dokumenttype = dokumenttype;
    }

    public byte[] getData() {
        return data;
    }

    public String getTittel() {
        return tittel;
    }

    public String getDokumenttype() {
        return dokumenttype;
    }
}
