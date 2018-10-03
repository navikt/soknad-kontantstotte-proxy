package no.nav.kontantstotte.proxy.api.rest.mottak;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.Soknad;

import static java.time.Instant.now;

class SoknadConverter {

    Soknad toSoknad(SoknadDto dto) {
        return new Soknad(
                dto.getFnr(),
                dto.getPdf(),
                dto.getInnsendingTimestamp() != null ? dto.getInnsendingTimestamp() : now());
    }

}
