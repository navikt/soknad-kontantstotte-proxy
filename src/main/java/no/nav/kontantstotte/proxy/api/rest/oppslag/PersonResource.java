package no.nav.kontantstotte.proxy.api.rest.oppslag;

import no.finn.unleash.Unleash;
import no.nav.kontantstotte.proxy.oppslag.person.domain.Person;
import no.nav.kontantstotte.proxy.oppslag.person.domain.PersonService;
import no.nav.kontantstotte.proxy.oppslag.person.domain.PersonServiceException;
import no.nav.security.oidc.api.ProtectedWithClaims;
import no.nav.security.oidc.api.Unprotected;
import no.nav.security.oidc.context.OIDCValidationContext;
import no.nav.security.oidc.jaxrs.OidcRequestContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("person")
@ProtectedWithClaims(issuer = "selvbetjening", claimMap = { "acr=Level4" })
public class PersonResource {

    private static final String SELVBETJENING = "selvbetjening";
    private static final String BRUK_TPS_INTEGRASJON = "kontantstotte.integrasjon.tps";

    private final PersonService personService;
    private final Unleash unleash;

    @Inject
    public PersonResource(PersonService personService, Unleash unleash) {
        this.personService = personService;
        this.unleash = unleash;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Person hentPerson() throws PersonServiceException {
        if(unleash.isEnabled(BRUK_TPS_INTEGRASJON) ) {
            return personService.hentPersonInfo(extractFnr());
        } else {
            throw new WebApplicationException(Response.Status.NOT_IMPLEMENTED);
        }
    }

    private static String extractFnr() {
        OIDCValidationContext context = OidcRequestContext.getHolder().getOIDCValidationContext();
        return context.getClaims(SELVBETJENING).getClaimSet().getSubject();
    }

}
