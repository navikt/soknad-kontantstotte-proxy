package no.nav.kontantstotte.proxy.api.rest.mottak;

import no.nav.kontantstotte.innsending.domene.SoknadSender;
import no.nav.security.oidc.api.Unprotected;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("soknad")
public class SoknadMottakResource {

    private final SoknadSender soknadSender;

    private final SoknadConverter converter = new SoknadConverter();

    @Inject
    public SoknadMottakResource(SoknadSender soknadSender) {

        this.soknadSender = soknadSender;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Unprotected // TODO Add protection at some point!
    public Response mottaSoknad() {
        SoknadDto dto = new SoknadDto("fnr", "bytes".getBytes());
        soknadSender.send(converter.toSoknad(dto));
        return Response.ok().entity(dto).build();
    }

}
