package no.nav.kontantstøtte.proxy.api.rest.exceptionmapper;

import no.nav.kontantstøtte.proxy.oppslag.person.domain.PersonServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class SøknadInnsendingExceptionMapper implements ExceptionMapper<PersonServiceException> {

    private static final Logger logger = LoggerFactory.getLogger(SøknadInnsendingExceptionMapper.class);

    @Override
    public Response toResponse(PersonServiceException e) {
        logger.warn(e.getMessage(), e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
