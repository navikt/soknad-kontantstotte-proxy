package no.nav.kontantstotte.proxy.dokumentinnsending.dokmot;

import no.nav.kontantstotte.proxy.dokumentinnsending.domain.Soknad;
import no.nav.kontantstotte.proxy.dokumentinnsending.domain.SoknadSender;
import no.nav.servlet.callid.CallId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;

public class DokmotJMSSender implements SoknadSender {

    private final DokmotConnection dokmotConnection;
    private final DokmotKontantstotteXMLKonvoluttGenerator generator = new DokmotKontantstotteXMLKonvoluttGenerator();

    private static final Logger LOG = LoggerFactory.getLogger(DokmotJMSSender.class);

    DokmotJMSSender(JmsTemplate template, QueueConfigration queueConfig) {
        this.dokmotConnection = new DokmotConnection(template, queueConfig);
    }

    @Override
    public void send(Soknad soknad) {
        if (dokmotConnection.isEnabled()) {
            String ref = CallId.getOrCreate();
            dokmotConnection.send(session -> {
                LOG.info("Sender SøknadsXML til DOKMOT");
                TextMessage msg = session.createTextMessage(generator.toXML(soknad));
                msg.setStringProperty("callId", ref);

                return msg;
            });


        } else {
            LOG.info("Leveranse til DOKMOT er deaktivert, ingenting å sende");
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [dokmotTemplate=" + dokmotConnection + ", generator=" + generator + "]";
    }

}
