package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadSender;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import static com.ibm.mq.constants.CMQC.MQENC_NATIVE;
import static com.ibm.msg.client.jms.JmsConstants.JMS_IBM_CHARACTER_SET;
import static com.ibm.msg.client.jms.JmsConstants.JMS_IBM_ENCODING;
import static com.ibm.msg.client.wmq.common.CommonConstants.WMQ_CM_CLIENT;

@Profile("!dev")
@Configuration
@EnableConfigurationProperties(QueueConfigration.class)
public class DokmotConfiguration {

    private static final int UTF_8_WITH_PUA = 1208;

    @Bean
    public JmsTemplate dokmotTemplate(QueueConfigration cfg, ConnectionFactory cf) {
        JmsTemplate jmsTemplate = new JmsTemplate(cf);
        jmsTemplate.setDefaultDestinationName(cfg.getQueuename());
        jmsTemplate.setDestinationResolver(new DynamicDestinationResolver());
        return jmsTemplate;
    }

    @Bean
    public MQQueueConnectionFactory connectionFactory(QueueConfigration cfg) throws JMSException {

        MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
        cf.setHostName(cfg.getHostname());
        cf.setTransportType(WMQ_CM_CLIENT);
        cf.setCCSID(UTF_8_WITH_PUA);
        cf.setPort(cfg.getPort());
        cf.setChannel(cfg.getChannelname());
        cf.setQueueManager(cfg.getName());
        cf.setIntProperty(JMS_IBM_ENCODING, MQENC_NATIVE);
        cf.setIntProperty(JMS_IBM_CHARACTER_SET, UTF_8_WITH_PUA);
        return cf;
    }

    @Bean
    @Primary
    ConnectionFactory userCredentialsConnectionFactoryAdapter(
            QueueConfigration cfg,
            MQQueueConnectionFactory delegate) {
        UserCredentialsConnectionFactoryAdapter cf = new UserCredentialsConnectionFactoryAdapter();
        cf.setUsername(cfg.getUsername());
        cf.setTargetConnectionFactory(delegate);
        return cf;
    }

    @Bean
    public SoknadSender soknadSender(JmsTemplate template, QueueConfigration queueConfig) {
        return new DokmotJMSSender(template, queueConfig);
    }

    @Bean
    public DokmotHealthIndicator dokmotHealthIndicator(JmsTemplate template, QueueConfigration queueConfig) {
        return new DokmotHealthIndicator(template, queueConfig);
    };

}
