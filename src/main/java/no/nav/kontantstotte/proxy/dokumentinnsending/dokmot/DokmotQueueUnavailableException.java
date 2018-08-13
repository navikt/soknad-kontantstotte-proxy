package no.nav.kontantstotte.proxy.dokumentinnsending.dokmot;

import no.nav.kontantstotte.proxy.dokumentinnsending.domain.SoknadInnsendingException;

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
