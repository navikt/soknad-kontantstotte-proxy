package no.nav.kontantstotte.proxy.api.rest.mottak;

import no.finn.unleash.Unleash;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadSender;
import no.nav.security.oidc.api.ProtectedWithClaims;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("soknad")
public class SoknadMottakResource {

    private static final String BRUK_DOKMOT_INTEGRASJON = "kontantstotte.integrasjon.dokmot";

    private final SoknadSender soknadSender;

    private final SoknadConverter converter = new SoknadConverter();

    private final Unleash unleash;

    @Inject
    public SoknadMottakResource(SoknadSender soknadSender, Unleash unleash) {

        this.soknadSender = soknadSender;
        this.unleash = unleash;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @ProtectedWithClaims(issuer = "selvbetjening", claimMap = { "acr=Level4" })
    public Response mottaSoknad(SoknadDto soknadDto) {
        if(unleash.isEnabled(BRUK_DOKMOT_INTEGRASJON)) {
            soknadSender.send(converter.toSoknad(soknadDto));
            return Response.ok().build();
        } else {
            throw new WebApplicationException(Response.Status.NOT_IMPLEMENTED);
        }
    }
}
