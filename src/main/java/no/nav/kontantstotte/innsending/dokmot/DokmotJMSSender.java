package no.nav.kontantstotte.innsending.dokmot;

import no.nav.kontantstotte.innsending.domene.Soknad;
import no.nav.kontantstotte.innsending.domene.SoknadSender;
import no.nav.servlet.callid.CallId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;

@Service
public class DokmotJMSSender implements SoknadSender {

    private final DokmotConnection dokmotConnection;
    private final DokmotEngangsstonadXMLKonvoluttGenerator generator;
//    private final CallIdGenerator idGenerator;

    private static final Logger LOG = LoggerFactory.getLogger(DokmotJMSSender.class);

    public DokmotJMSSender(
            DokmotConnection connection,
//            CallIdGenerator callIdGenerator,
            DokmotEngangsstonadXMLKonvoluttGenerator generator) {
        this.dokmotConnection = connection;
        this.generator = generator;
//        this.idGenerator = callIdGenerator;
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
//            return new Kvittering(PÅ_VENT, ref);
        }
        LOG.info("Leveranse til DOKMOT er deaktivert, ingenting å sende");
//        return new Kvittering(IKKE_SENDT_FPSAK, "42");
    }

//    @Override
//    public Kvittering send(Ettersending ettersending, Person søker) {
//        throw new IllegalArgumentException("Ettersending for engangsstønad ikke implementert");
//    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [dokmotTemplate=" + dokmotConnection + ", generator=" + generator
//                + ", callIdGenerator=" + idGenerator
                + "]";
    }

}
