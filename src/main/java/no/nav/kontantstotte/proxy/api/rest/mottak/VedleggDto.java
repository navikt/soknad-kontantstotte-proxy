package no.nav.kontantstotte.proxy.api.rest.mottak;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VedleggDto {

    @JsonProperty
    private final byte[] data;

    @JsonProperty
    private final String tittel;

    @JsonProperty
    private final String dokumenttype;

    public VedleggDto(
            @JsonProperty("data") byte[] data,
            @JsonProperty("tittel") String tittel,
            @JsonProperty("dokumenttype") String dokumenttype) {
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
