package no.nav.kontantstotte.proxy.api.rest.exceptionmapper;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Jersey specific :-/
 */
public class ExceptionLogger implements ApplicationEventListener, RequestEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionLogger.class);

    @Override
    public void onEvent(ApplicationEvent event) {}

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return this;
    }

    @Override
    public void onEvent(RequestEvent event) {
        if(event.getType() == RequestEvent.Type.ON_EXCEPTION) {
            logger.warn("Exception som ikke ble fanget: ", event.getException());
        }

    }
}
