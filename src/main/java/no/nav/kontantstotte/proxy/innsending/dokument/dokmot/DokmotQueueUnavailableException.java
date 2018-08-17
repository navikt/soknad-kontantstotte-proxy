package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadInnsendingException;

class DokmotQueueUnavailableException extends SoknadInnsendingException {

    private final QueueConfigration config;

    DokmotQueueUnavailableException(Exception e, QueueConfigration config) {
        super(e);
        this.config = config;
    }

    public QueueConfigration getConfig() {
        return config;
    }
}
