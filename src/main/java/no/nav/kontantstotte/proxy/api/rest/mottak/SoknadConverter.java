package no.nav.kontantstotte.proxy.api.rest.mottak;

import no.nav.kontantstotte.innsending.domene.Soknad;

class SoknadConverter {

    Soknad toSoknad(SoknadDto dto) {
        return new Soknad(dto.getFnr(), dto.getPdf());
    }

}
