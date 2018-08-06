//package no.nav.kontantstotte.innsending.dokmot;
//
//import no.nav.servlet.callid.CallId;
//import org.springframework.jms.core.MessagePostProcessor;
//
//import javax.jms.JMSException;
//import javax.jms.Message;
//
//public class CallIdAppendingPostProcessor implements MessagePostProcessor {
//
//    @Override
//    public Message postProcessMessage(Message message) throws JMSException {
//        message.setStringProperty("callId", CallId.getOrCreate());
//        return message;
//    }
//
//}
