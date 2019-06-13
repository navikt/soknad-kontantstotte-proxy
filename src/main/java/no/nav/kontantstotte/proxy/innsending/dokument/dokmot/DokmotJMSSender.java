package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.Soknad;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadSender;
import no.nav.log.MDCConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.TextMessage;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class DokmotJMSSender implements SoknadSender {

    private static final Logger LOG = LoggerFactory.getLogger(DokmotJMSSender.class);

    private final DokmotKontantstotteXMLKonvoluttGenerator generator = new DokmotKontantstotteXMLKonvoluttGenerator();

    private final QueueConfiguration queueConfig;

    private final JmsTemplate template;

    private final Counter dokmotSuccess = Metrics.counter("dokmot.send", "soknad", "success");
    private final Counter dokmotFailure = Metrics.counter("dokmot.send", "soknad", "failure");
    private final DistributionSummary dokmotMeldingStorrelse = Metrics.summary("dokmot.melding.storrelse");
    private final Timer dokmotResponstid = Metrics.timer("dokmot.respons.tid");

    DokmotJMSSender(JmsTemplate template, QueueConfiguration queueConfig) {
        this.queueConfig = queueConfig;
        this.template = template;
    }

    @Override
    public void send(Soknad soknad) {
        if (!queueConfig.isEnabled()) {
            LOG.info("Leveranse til DOKMOT er deaktivert, ingenting å sende");
            return;
        }

        try {
            String soknadXML = generator.toXML(soknad);
            dokmotMeldingStorrelse.record(Base64.getEncoder().encode(soknadXML.getBytes()).length);
            long startTime = System.nanoTime();
            LOG.info(soknadXML);
            template.send(session -> {
                LOG.info("Sender SoknadsXML til DOKMOT");
                TextMessage msg = session.createTextMessage(soknadXML);
                msg.setStringProperty("callId", MDC.get(MDCConstants.MDC_CORRELATION_ID));
                return msg;
            });
            dokmotResponstid.record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
            dokmotSuccess.increment();
        } catch (JmsException e) {
            dokmotFailure.increment();
            throw new DokmotQueueUnavailableException(e, queueConfig);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [template=" + template + ", queueConfig=" + queueConfig.loggable() + ", generator=" + generator + "]";
    }

}
