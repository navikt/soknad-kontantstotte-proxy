package no.nav.kontantstotte.proxy.dokumentinnsending.dokmot;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import java.util.Arrays;

class DokmotHealthIndicator implements HealthIndicator, EnvironmentAware {

    private static final Logger LOG = LoggerFactory.getLogger(DokmotHealthIndicator.class);

    private final Counter dokmotSuccess = Metrics.counter("dokmot.health", "response", "success");
    private final Counter dokmotFailure = Metrics.counter("dokmot.health", "response", "failure");

    private Environment env;

    private final JmsTemplate template;
    private final QueueConfigration queueConfig;

    DokmotHealthIndicator(
            JmsTemplate template,
            QueueConfigration queueConfig) {
        this.template = template;
        this.queueConfig = queueConfig;
    }

    @Override
    public Health health() {
        if (isDev()) {
            LOG.info("In DEV mode, not verifying health of DOKMOT");
            return up();
        }

        LOG.info("Pinging DOKMOT {}", queueConfig.loggable());
        try {
            template.getConnectionFactory().createConnection().close();
            dokmotSuccess.increment();
            return isPreprod() ? upWithDetails() : up();
        } catch (JMSException e) {
            dokmotFailure.increment();
            LOG.warn("Could not verify health of DOKMOT {}", queueConfig.loggable(), e);
            return isPreprod() ? downWithDetails(e) : down();
        }
    }

    private static Health down() {
        return Health.down().build();
    }

    private Health downWithDetails(Exception e) {
        return Health.down().withDetail("messagequeue", queueConfig.loggable()).withException(e).build();
    }

    private boolean isPreprod() {
        return env.acceptsProfiles("preprod");
    }

    private boolean isDev() {
        return env.acceptsProfiles("dev");
    }

    private static Health up() {
        return Health.up().build();
    }

    private Health upWithDetails() {
        return Health.up().withDetail("messagequeue", queueConfig.loggable()).build();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [pinger=" + queueConfig.loggable() + ", activeProfiles "
                + Arrays.toString(env.getActiveProfiles()) + "]";
    }

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
    }
}
