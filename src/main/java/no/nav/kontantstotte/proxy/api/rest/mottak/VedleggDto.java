package no.nav.kontantstotte.proxy.api.rest.mottak;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VedleggDto {

    @JsonProperty
    private final byte[] data;

    @JsonProperty
    private final String tittel;

    public VedleggDto(
            @JsonProperty("data") byte[] data,
            @JsonProperty("tittel") String tittel) {
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
