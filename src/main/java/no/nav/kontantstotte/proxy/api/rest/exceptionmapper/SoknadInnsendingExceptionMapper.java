package no.nav.kontantstotte.proxy.api.rest.exceptionmapper;

import no.nav.kontantstotte.proxy.person.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class SoknadInnsendingExceptionMapper implements ExceptionMapper<ServiceException> {

    private static final Logger logger = LoggerFactory.getLogger(SoknadInnsendingExceptionMapper.class);

    @Override
    public Response toResponse(ServiceException e) {
        logger.warn(e.getMessage(), e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
