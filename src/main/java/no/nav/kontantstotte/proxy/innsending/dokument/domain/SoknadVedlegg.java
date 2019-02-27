package no.nav.kontantstotte.proxy.innsending.dokument.domain;

public class SoknadVedlegg {

    private final byte[] data;
    private final String tittel;

    public SoknadVedlegg(byte[] data, String tittel) {
        this.data = data;
        this.tittel = tittel;
    }

    public byte[] getData() {
        return data;
    }

    public String getTittel() {
        return tittel;
    }
}
