package no.nav.kontantstotte.proxy.api.rest.exceptionmapper;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadInnsendingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class SoknadInnsendingExceptionMapper implements ExceptionMapper<SoknadInnsendingException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoknadInnsendingExceptionMapper.class);

    @Override
    public Response toResponse(SoknadInnsendingException e) {
        LOGGER.warn("Innsending til DOKMOT feilet. Config: {}", e.getConfig(), e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
