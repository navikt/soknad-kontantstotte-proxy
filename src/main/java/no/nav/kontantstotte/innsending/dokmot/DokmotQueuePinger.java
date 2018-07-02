package no.nav.kontantstotte.innsending.dokmot;

import no.nav.kontantstotte.innsending.config.QueueConfigration;
import org.springframework.stereotype.Component;

@Component
public class DokmotQueuePinger {

    private final DokmotConnection connection;

    public DokmotQueuePinger(DokmotConnection connection) {
        this.connection = connection;
    }

    public void ping() {
        connection.ping();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [connection=" + connection + "]";
    }
}
