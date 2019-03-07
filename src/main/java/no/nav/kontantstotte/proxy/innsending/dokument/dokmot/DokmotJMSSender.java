package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

import io.micrometer.core.instrument.Gauge;
import io.prometheus.client.CollectorRegistry;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.Soknad;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadSender;
import no.nav.kontantstotte.proxy.metrics.MetricService;
import no.nav.log.MDCConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.TextMessage;

public class DokmotJMSSender implements SoknadSender {

    private static final Logger LOG = LoggerFactory.getLogger(DokmotJMSSender.class);

    private final DokmotKontantstotteXMLKonvoluttGenerator generator = new DokmotKontantstotteXMLKonvoluttGenerator();

    private final QueueConfiguration queueConfig;

    private final JmsTemplate template;

    private final MetricService metricService;

    DokmotJMSSender(JmsTemplate template, QueueConfiguration queueConfig, MetricService metricService) {
        this.queueConfig = queueConfig;
        this.template = template;
        this.metricService = metricService;
    }

    @Override
    public void send(Soknad soknad) {
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
            metricService.getDokmotStatus().labels("success", "-").inc();
        } catch (JmsException e) {
            metricService.getDokmotStatus().labels("failure", soknad.getFnr()).inc();
            throw new DokmotQueueUnavailableException(e, queueConfig);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [template=" + template + ", queueConfig=" + queueConfig.loggable() + ", generator=" + generator + "]";
    }

}
