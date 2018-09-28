package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.Soknad;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.*;

public class DokmotJmsSoknadSenderTest {

    private QueueConfiguration queueConfig = mock(QueueConfiguration.class);

    private JmsTemplate template = mock(JmsTemplate.class);

    private DokmotJMSSender soknadSender = new DokmotJMSSender(template, queueConfig);

    @Test
    public void that_disabled_queues_do_not_get_called() {

        when(queueConfig.isEnabled()).thenReturn(false);

        soknadSender.send(new Soknad("MASKERT_FNR", "test".getBytes(), now()));

        verifyZeroInteractions(template);
    }

    @Test
    public void successful_send() {
        when(queueConfig.isEnabled()).thenReturn(true);

        soknadSender.send(new Soknad("MASKERT_FNR", "test".getBytes(), now()));

        verify(template).send(any());
    }

}
