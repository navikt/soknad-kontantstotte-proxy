package no.nav.kontantstotte.innsending.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "dokmot")
@Configuration
public class QueueConfigration {

    @NotNull
    private String hostname;
    @Positive
    private int port;
    @NotNull
    private String name;
    @NotNull
    private String channelname;
    @NotNull
    private String username;
    @NotNull
    private String queuename;

    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public String getChannelname() {
        return channelname;
    }

    public String getUsername() {
        return username;
    }

    public String getQueuename() {
        return queuename;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setQueuename(String queuename) {
        this.queuename = queuename;
    }

    public QueueConfigration loggable() {
        QueueConfigration unwrapped = new QueueConfigration();
        unwrapped.setChannelname(getChannelname());
        unwrapped.setHostname(getHostname());
        unwrapped.setPort(getPort());
        unwrapped.setQueuename(getQueuename());
        return unwrapped;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [hostname=" + hostname + ", port=" + port + ", name=" + name
                + ", channelname="
                + channelname + ", username=" + "********" + ", queuename=" + queuename + "]";
    }

}
