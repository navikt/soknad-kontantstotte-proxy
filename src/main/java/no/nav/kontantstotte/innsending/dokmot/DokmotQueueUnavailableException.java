package no.nav.kontantstotte.innsending.dokmot;

import no.nav.kontantstotte.innsending.config.QueueConfigration;

public class DokmotQueueUnavailableException extends RuntimeException {

    private final QueueConfigration config;

    public DokmotQueueUnavailableException(Exception e, QueueConfigration config) {
        super(e);
        this.config = config;
    }

    public QueueConfigration getConfig() {
        return config;
    }
}
