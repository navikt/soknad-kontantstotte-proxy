package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

public class DokmotQueueUnavailableException extends RuntimeException {

    private final QueueConfiguration config;

    public DokmotQueueUnavailableException(Exception e, QueueConfiguration config) {
        super(e);
        this.config = config;
    }

    public QueueConfiguration getConfig() {
        return config;
    }
}
