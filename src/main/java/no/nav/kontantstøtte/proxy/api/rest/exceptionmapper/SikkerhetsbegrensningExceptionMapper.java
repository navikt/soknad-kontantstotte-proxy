package no.nav.kontantstøtte.proxy.api.rest.exceptionmapper;

import no.nav.kontantstøtte.proxy.oppslag.person.domain.SikkerhetsbegrensningExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class SikkerhetsbegrensningExceptionMapper implements ExceptionMapper<SikkerhetsbegrensningExeption> {

    private static final Logger LOG = LoggerFactory.getLogger(SikkerhetsbegrensningExceptionMapper.class);

    @Override
    public Response toResponse(SikkerhetsbegrensningExeption exception) {
        LOG.warn(exception.getMessage());
        return Response.status(Response.Status.FORBIDDEN).build();
    }


}
