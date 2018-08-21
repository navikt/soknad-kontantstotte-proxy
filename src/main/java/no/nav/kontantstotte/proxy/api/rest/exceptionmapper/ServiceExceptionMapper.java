package no.nav.kontantstotte.proxy.api.rest.exceptionmapper;

import no.nav.kontantstotte.proxy.oppslag.person.domain.PersonServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ServiceExceptionMapper implements ExceptionMapper<PersonServiceException> {

    private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionMapper.class);

    @Override
    public Response toResponse(PersonServiceException exception) {
        logger.warn(exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
