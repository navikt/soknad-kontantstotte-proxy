package no.nav.kontantstotte.proxy.api.rest.mottak;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SoknadDto {

    @JsonProperty
    private final byte[] pdf;

    @JsonProperty
    private final String fnr;

    @JsonProperty
    private final Instant innsendingsTidspunkt;

    @JsonCreator
    public SoknadDto(
            @JsonProperty("fnr") String fnr,
            @JsonProperty("pdf") byte[] pdf,
            @JsonProperty("innsendingsTidspunkt") Instant innsendingsTidspunkt) {
        this.pdf = pdf;
        this.fnr = fnr;
        this.innsendingsTidspunkt = innsendingsTidspunkt;
    }

    byte[] getPdf() {
        return pdf;
    }

    String getFnr() {
        return fnr;
    }

    Instant getInnsendingsTidspunkt() {
        return innsendingsTidspunkt;
    }
}
