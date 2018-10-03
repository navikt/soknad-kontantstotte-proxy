package no.nav.kontantstotte.proxy.api.rest.mottak;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SoknadDto {

    @JsonProperty
    private final byte[] pdf;

    @JsonProperty
    private final String fnr;

    @JsonProperty
    private final Instant innsendingTimestamp;

    @JsonCreator
    public SoknadDto(
            @JsonProperty("fnr") String fnr,
            @JsonProperty("pdf") byte[] pdf,
            @JsonProperty("innsendingTimestamp") Instant innsendingTimestamp) {
        this.pdf = pdf;
        this.fnr = fnr;
        this.innsendingTimestamp = innsendingTimestamp;
    }

    byte[] getPdf() {
        return pdf;
    }

    String getFnr() {
        return fnr;
    }

    Instant getInnsendingTimestamp() {
        return innsendingTimestamp;
    }
}
