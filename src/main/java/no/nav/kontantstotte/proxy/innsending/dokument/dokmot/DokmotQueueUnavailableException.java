package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadInnsendingException;

class DokmotQueueUnavailableException extends SoknadInnsendingException {

    DokmotQueueUnavailableException(Exception e, QueueConfiguration config) {
        super(config.toString(), e);
    }

}
