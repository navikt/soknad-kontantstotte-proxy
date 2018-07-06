package no.nav.kontantstotte.proxy.service.ws;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@Component
public class UUIDCallIdGenerator {

    private final String callIdKey;

    @Inject
    public UUIDCallIdGenerator(@Value("${callid.key:Nav-CallId}") String callIdKey) {
        this.callIdKey = callIdKey;
    }

    public String getOrCreate() {
        return Optional.ofNullable(MDC.get(callIdKey)).orElse(create());
    }

    public String create() {
        return UUID.randomUUID().toString();
    }

    public String getCallIdKey() {
        return callIdKey;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [callIdKey=" + callIdKey + "]";
    }
}
