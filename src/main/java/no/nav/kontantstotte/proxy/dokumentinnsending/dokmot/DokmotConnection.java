package no.nav.kontantstotte.proxy.dokumentinnsending.dokmot;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

class DokmotConnection {

    private static final Logger LOG = LoggerFactory.getLogger(DokmotConnection.class);

    private final Counter dokmotSuccess = Metrics.counter("dokmot.send", "søknad", "success");
    private final Counter dokmotFailure = Metrics.counter("dokmot.send", "søknad", "failure");

    private final JmsTemplate template;
    private final QueueConfigration queueConfig;

    DokmotConnection(JmsTemplate template, QueueConfigration queueConfig) {
        this.template = template;
        this.queueConfig = queueConfig;

        LOG.info("QueueConfiguration: " + queueConfig.toString());
    }

    void send(MessageCreator msg) {
        try {
            template.send(msg);
            dokmotSuccess.increment();
        } catch (JmsException e) {
            LOG.warn("Unable to send to DOKMOT at {}", queueConfig.loggable(), e);
            dokmotFailure.increment();
            throw new DokmotQueueUnavailableException(e, queueConfig);
        }
    }

    boolean isEnabled() {
        return queueConfig.isEnabled();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [template=" + template + ", queueConfig=" + queueConfig.loggable() + "]";
    }
}
