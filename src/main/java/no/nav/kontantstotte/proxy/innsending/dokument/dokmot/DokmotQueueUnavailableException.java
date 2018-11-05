package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadInnsendingException;

public class DokmotQueueUnavailableException extends SoknadInnsendingException {

    private final QueueConfiguration config;

    public DokmotQueueUnavailableException(Exception e, QueueConfiguration config) {
        super(e);
        this.config = config;
    }

    public QueueConfiguration getConfig() {
        return config;
    }
}
