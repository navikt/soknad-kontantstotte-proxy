package no.nav.kontantstotte.proxy.api.rest.mottak;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.Instant;

import java.time.LocalDateTime;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SoknadDto {

    @JsonProperty
    private final byte[] pdf;

    @JsonProperty
    private final String fnr;

    @JsonProperty
    private final LocalDateTime innsendingTimestamp;

    @JsonCreator
    public SoknadDto(
            @JsonProperty("fnr") String fnr,
            @JsonProperty("pdf") byte[] pdf,
            @JsonProperty("innsendingTimestamp") LocalDateTime innsendingTimestamp) {
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

    LocalDateTime getInnsendingTimestamp() {
        return innsendingTimestamp;
    }
}
