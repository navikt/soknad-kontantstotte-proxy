package no.nav.kontantstotte.proxy.api.rest.exceptionmapper;

import no.nav.kontantstotte.proxy.innsending.dokument.dokmot.DokmotQueueUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.core.Response;

public class DokmotQueueUnavailableExceptionMapper implements ExceptionMapper<DokmotQueueUnavailableException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DokmotQueueUnavailableExceptionMapper.class);

    @Override
    public Response toResponse(DokmotQueueUnavailableException e) {
        LOGGER.warn("Unable to send to DOKMOT at {}", e.getConfig().loggable(), e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
