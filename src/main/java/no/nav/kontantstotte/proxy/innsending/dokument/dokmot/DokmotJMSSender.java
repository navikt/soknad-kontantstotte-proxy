package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.Soknad;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadSender;
import no.nav.log.MDCConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.TextMessage;

public class DokmotJMSSender implements SoknadSender {

    private static final Logger LOG = LoggerFactory.getLogger(DokmotJMSSender.class);

    private final Counter dokmotSuccess = Metrics.counter("dokmot.send", "soknad", "success");
    private final Counter dokmotFailure = Metrics.counter("dokmot.send", "soknad", "failure");
    private final DokmotKontantstotteXMLKonvoluttGenerator generator = new DokmotKontantstotteXMLKonvoluttGenerator();

    private final QueueConfiguration queueConfig;

    private final JmsTemplate template;

    DokmotJMSSender(JmsTemplate template, QueueConfiguration queueConfig) {
        this.queueConfig = queueConfig;
        this.template = template;
    }

    @Override
    public void send(Soknad soknad) {
        /*
        if (!queueConfig.isEnabled()) {
            LOG.info("Leveranse til DOKMOT er deaktivert, ingenting å sende");
            return;
        }

        try {
            template.send(session -> {
                LOG.info("Sender SoknadsXML til DOKMOT");
                TextMessage msg = session.createTextMessage(generator.toXML(soknad));
                msg.setStringProperty("callId", MDC.get(MDCConstants.MDC_CORRELATION_ID));

                return msg;
            });
            dokmotSuccess.increment();
        } catch (JmsException e) {
        */
        Exception testException = new RuntimeException("test");
        dokmotFailure.increment();
        throw new DokmotQueueUnavailableException(testException, queueConfig);
        //}
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [template=" + template + ", queueConfig=" + queueConfig.loggable() + ", generator=" + generator + "]";
    }

}
