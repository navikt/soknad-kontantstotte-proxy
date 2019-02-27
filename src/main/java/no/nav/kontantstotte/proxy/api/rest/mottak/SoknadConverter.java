package no.nav.kontantstotte.proxy.api.rest.mottak;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.Soknad;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadVedlegg;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.Instant.now;

class SoknadConverter {

    Soknad toSoknad(SoknadDto dto) {
        return new Soknad(
                dto.getFnr(),
                dto.getPdf(),
                dto.getInnsendingsTidspunkt() != null ? dto.getInnsendingsTidspunkt() : now(),
                toVedlegg(dto.getVedlegg()));
    }

    private List<SoknadVedlegg> toVedlegg(List<VedleggDto> vedlegg) {
        if(vedlegg == null)
            return Collections.emptyList();

        return vedlegg.stream()
                .map(v -> new SoknadVedlegg(v.getData(), v.getTittel()))
                .collect(Collectors.toList());
    }

}
