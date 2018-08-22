package no.nav.kontantstøtte.proxy.innsending.dokument.dokmot;

import no.nav.kontantstøtte.proxy.innsending.dokument.domain.SøknadInnsendingException;

class DokmotQueueUnavailableException extends SøknadInnsendingException {

    private final QueueConfiguration config;

    DokmotQueueUnavailableException(Exception e, QueueConfiguration config) {
        super(e);
        this.config = config;
    }

    public QueueConfiguration getConfig() {
        return config;
    }
}
