package no.nav.kontantstotte.proxy.api.rest.mottak;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.Søknad;

class SøknadConverter {

    Søknad toSøknad(SoknadDto dto) {
        return new Søknad(dto.getFnr(), dto.getPdf());
    }

}
