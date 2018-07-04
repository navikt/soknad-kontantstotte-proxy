package no.nav.kontantstotte.proxy.api.rest.exceptionmapper;

import no.nav.kontantstotte.proxy.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

    private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionMapper.class);

    @Override
    public Response toResponse(ServiceException exception) {
        logger.warn(exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}