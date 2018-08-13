package no.nav.kontantstotte.proxy.api.rest.mottak;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SoknadDto {

    private final byte[] pdf;

    private final String fnr;

    @JsonCreator
    SoknadDto(
            @JsonProperty("fnr") String fnr,
            @JsonProperty("pdf") byte[] pdf) {
        this.pdf = pdf;
        this.fnr = fnr;
    }

    byte[] getPdf() {
        return pdf;
    }

    String getFnr() {
        return fnr;
    }



}
