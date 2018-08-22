package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.Søknad;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

import static org.mockito.Mockito.*;

public class DokmotJmsSøknadSenderTest {

    private QueueConfiguration queueConfig = mock(QueueConfiguration.class);

    private JmsTemplate template = mock(JmsTemplate.class);

    private DokmotJMSSender søknadSender = new DokmotJMSSender(template, queueConfig);

    @Test
    public void that_disabled_queues_do_not_get_called() {

        when(queueConfig.isEnabled()).thenReturn(false);

        søknadSender.send(new Søknad("12345678901", "test".getBytes()));

        verifyZeroInteractions(template);
    }

    @Test
    public void successful_send() {
        when(queueConfig.isEnabled()).thenReturn(true);

        søknadSender.send(new Søknad("12345678901", "test".getBytes()));

        verify(template).send(any());
    }

}
