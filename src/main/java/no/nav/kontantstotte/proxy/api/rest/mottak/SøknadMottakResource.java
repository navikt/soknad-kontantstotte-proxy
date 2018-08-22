package no.nav.kontantstotte.proxy.api.rest.mottak;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.SøknadSender;
import no.nav.security.oidc.api.ProtectedWithClaims;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("soknad")
public class SøknadMottakResource {

    private final SøknadSender søknadSender;

    private final SøknadConverter converter = new SøknadConverter();

    @Inject
    public SøknadMottakResource(SøknadSender søknadSender) {

        this.søknadSender = søknadSender;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @ProtectedWithClaims(issuer = "selvbetjening", claimMap = { "acr=Level4" })
    public Response mottaSøknad(SoknadDto soknadDto) {
        søknadSender.send(converter.toSøknad(soknadDto));
        return Response.ok().build();
    }

}
