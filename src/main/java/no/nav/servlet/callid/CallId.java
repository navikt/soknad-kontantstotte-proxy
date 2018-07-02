package no.nav.servlet.callid;

import org.slf4j.MDC;

import java.util.Optional;
import java.util.UUID;

public class CallId {

    public static final String DEFAULT_CALL_ID_KEY = "Nav-CallId";

    public static String getOrCreate() {
        return Optional.ofNullable(MDC.get(DEFAULT_CALL_ID_KEY)).orElseGet(CallId::create);
    }

    public static String get() {
        return MDC.get(DEFAULT_CALL_ID_KEY);
    }

    public static String create() {
        set(UUID.randomUUID().toString());
        return MDC.get(DEFAULT_CALL_ID_KEY);
    }

    public static String set(String callId) {
        MDC.put(DEFAULT_CALL_ID_KEY, callId);
        return callId;
    }

}
