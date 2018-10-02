package no.nav.kontantstotte.proxy.api.rest.mottak;

import no.finn.unleash.Unleash;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadSender;
import no.nav.security.oidc.api.ProtectedWithClaims;
import no.nav.security.oidc.context.OIDCValidationContext;
import no.nav.security.oidc.jaxrs.OidcRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("soknad")
public class SoknadMottakResource {

    public static final String BRUK_DOKMOT_INTEGRASJON = "kontantstotte.integrasjon.dokmot";
    private static final Logger logger = LoggerFactory.getLogger(SoknadMottakResource.class);
    private static final String SELVBETJENING = "selvbetjening";
    private static final int MINIMUM_PDF_STORRELSE = 10000;

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
        if (!hentFnrFraToken().equals(soknadDto.getFnr())) {
            logger.warn("Fødselsnummer på innsendt søknad tilsvarer ikke innlogget bruker");
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        if (soknadDto.getPdf().length < MINIMUM_PDF_STORRELSE) {
            logger.warn("Størrelse på pdf er under minimumkravet og noe er sannsynligvis feil.");
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if(unleash.isEnabled(BRUK_DOKMOT_INTEGRASJON)) {
            soknadSender.send(converter.toSoknad(soknadDto));
            return Response.ok().build();
        } else {
            logger.warn("Feature toggle {} er skrudd av. Sjekk unleash.", BRUK_DOKMOT_INTEGRASJON);
            throw new WebApplicationException(Response.Status.NOT_IMPLEMENTED);
        }
    }

    private static String hentFnrFraToken() {
        OIDCValidationContext context = OidcRequestContext.getHolder().getOIDCValidationContext();
        return context.getClaims(SELVBETJENING).getClaimSet().getSubject();
    }
}
