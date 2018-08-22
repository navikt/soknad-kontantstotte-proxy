package no.nav.kontantstøtte.proxy.innsending.dokument.dokmot;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mock.env.MockEnvironment;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DokmotHealthIndicatorTest {

    private QueueConfiguration queueConfig = mock(QueueConfiguration.class);

    private JmsTemplate template = mock(JmsTemplate.class);

    private DokmotHealthIndicator dokmotHealthIndicator;

    private Environment mockedEnv = new MockEnvironment();

    private ConnectionFactory connectionFactory = mock(ConnectionFactory.class);

    private Connection connection = mock(Connection.class);

    @Before
    public void setUp() {
        when(template.getConnectionFactory()).thenReturn(connectionFactory);
        dokmotHealthIndicator = new DokmotHealthIndicator(template, queueConfig);
        dokmotHealthIndicator.setEnvironment(mockedEnv);
    }

    @Test
    public void when_everything_is_fine() throws JMSException {

        when(connectionFactory.createConnection()).thenReturn(connection);

        Health health = dokmotHealthIndicator.health();

        assertThat(health.getStatus(), is(equalTo(Status.UP)));
    }

    @Test
    public void that_health_reports_down_on_connection_exception() throws JMSException {

        when(connectionFactory.createConnection()).thenThrow(new JMSException("Reason"));

        Health health = dokmotHealthIndicator.health();

        assertThat(health.getStatus(), is(equalTo(Status.DOWN)));
    }
}
