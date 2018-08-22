package no.nav.kontantstøtte.proxy.api.rest.mottak;

import no.nav.kontantstøtte.proxy.innsending.dokument.domain.Søknad;

class SøknadConverter {

    Søknad toSøknad(SoknadDto dto) {
        return new Søknad(dto.getFnr(), dto.getPdf());
    }

}
