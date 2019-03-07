package no.nav.kontantstotte.proxy.metrics;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;

public class MetricService {

    private static Counter dokmotStatus;

    public MetricService() {
        CollectorRegistry registry = new CollectorRegistry();

        dokmotStatus = Counter.build()
                .name("dokmot_send")
                .help("Status for innsending til DOKMOT")
                .labelNames("status", "user")
                .register(registry);
    }

    public Counter getDokmotStatus() {
        return dokmotStatus;
    }
}
