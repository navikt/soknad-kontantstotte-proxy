package no.nav.kontantstotte.proxy.api.rest.mottak;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadSender;
import no.nav.security.oidc.api.ProtectedWithClaims;
import no.nav.security.oidc.context.OIDCValidationContext;
import no.nav.security.oidc.jaxrs.OidcRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import static no.nav.kontantstotte.proxy.config.toggle.UnleashProvider.toggle;

@Path("/")
@Component
public class SoknadMottakController {

    public static final String BRUK_DOKMOT_INTEGRASJON = "kontantstotte.integrasjon.dokmot";
    private static final Logger log = LoggerFactory.getLogger(SoknadMottakController.class);
    private static final String SELVBETJENING = "selvbetjening";
    public static final int MINIMUM_PDF_STORRELSE = 10000;

    private final SoknadSender soknadSender;

    private final SoknadConverter converter = new SoknadConverter();

    @Autowired
    public SoknadMottakController(SoknadSender soknadSender) {
        this.soknadSender = soknadSender;
    }

    @POST
    @Path("soknad")
    @ProtectedWithClaims(issuer = "selvbetjening", claimMap = { "acr=Level4" })
    public Response mottaSoknad(SoknadDto soknadDto) {
        if (!hentFnrFraToken().equals(soknadDto.getFnr())) {
            log.warn("Fødselsnummer på innsendt søknad tilsvarer ikke innlogget bruker");
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if (soknadDto.getPdf().length < MINIMUM_PDF_STORRELSE) {
            log.error("Størrelse på pdf er under minimumkravet og noe er sannsynligvis feil.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(toggle(BRUK_DOKMOT_INTEGRASJON).isEnabled()) {
            soknadSender.send(converter.toSoknad(soknadDto));
            return Response.ok().build();
        } else {
            log.warn("Feature toggle {} er skrudd av. Sjekk unleash.", BRUK_DOKMOT_INTEGRASJON);
            return Response.status(Response.Status.NOT_IMPLEMENTED).build();
        }
    }

    private static String hentFnrFraToken() {
        OIDCValidationContext context = OidcRequestContext.getHolder().getOIDCValidationContext();
        return context.getClaims(SELVBETJENING).getClaimSet().getSubject();
    }
}
