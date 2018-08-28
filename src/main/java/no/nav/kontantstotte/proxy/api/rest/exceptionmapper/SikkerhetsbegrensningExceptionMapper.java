package no.nav.kontantstotte.proxy.api.rest.exceptionmapper;

import no.nav.kontantstotte.proxy.oppslag.person.domain.SikkerhetsbegrensningException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class SikkerhetsbegrensningExceptionMapper implements ExceptionMapper<SikkerhetsbegrensningException> {

    private static final Logger LOG = LoggerFactory.getLogger(SikkerhetsbegrensningExceptionMapper.class);

    @Override
    public Response toResponse(SikkerhetsbegrensningException exception) {
        LOG.warn(exception.getMessage());
        return Response.status(Response.Status.FORBIDDEN).build();
    }


}
