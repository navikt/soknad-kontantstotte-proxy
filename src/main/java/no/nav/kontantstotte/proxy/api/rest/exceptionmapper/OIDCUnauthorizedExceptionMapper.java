package no.nav.kontantstotte.proxy.api.rest.exceptionmapper;

import no.nav.kontantstotte.proxy.oppslag.person.service.ws.OIDCUnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class OIDCUnauthorizedExceptionMapper implements ExceptionMapper<OIDCUnauthorizedException> {

    private static final Logger logger = LoggerFactory.getLogger(OIDCUnauthorizedExceptionMapper.class);

    @Override
    public Response toResponse(OIDCUnauthorizedException exception) {
        logger.warn(exception.getMessage());
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
