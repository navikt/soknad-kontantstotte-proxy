package no.nav.kontantstotte.proxy.api.rest.oppslag;

import no.finn.unleash.Unleash;
import no.nav.kontantstotte.proxy.oppslag.person.domain.Adresse;
import no.nav.kontantstotte.proxy.oppslag.person.domain.Person;
import no.nav.kontantstotte.proxy.oppslag.person.domain.PersonService;
import no.nav.kontantstotte.proxy.oppslag.person.domain.PersonServiceException;
import no.nav.security.oidc.api.ProtectedWithClaims;
import no.nav.security.oidc.context.OIDCValidationContext;
import no.nav.security.oidc.jaxrs.OidcRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(PersonResource.class);
    private static final String SELVBETJENING = "selvbetjening";
    public static final String BRUK_TPS_INTEGRASJON = "kontantstotte.integrasjon.tps";
    public static final String BRUK_MOCK_TPS_INTEGRASJON = "kontantstotte.integrasjon.mock.tps";

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
        } else if (unleash.isEnabled(BRUK_MOCK_TPS_INTEGRASJON)) {
            return new Person.Builder()
                    .fornavn("Ola")
                    .mellomnavn("T")
                    .slektsnavn("Nordmann")
                    .boadresse(new Adresse.Builder()
                            .adresse("Olaveien")
                            .adressetillegg("Tillegg")
                            .bydel("Ola")
                            .kommune("Ola")
                            .landkode("0123")
                            .postnummer("0123")
                            .build())
                    .build();
        } else {
            logger.warn("Feature toggle {} og {} er skrudd av. Sjekk unleash.", BRUK_TPS_INTEGRASJON, BRUK_MOCK_TPS_INTEGRASJON);
            throw new WebApplicationException(Response.Status.NOT_IMPLEMENTED);
        }
    }

    private static String extractFnr() {
        OIDCValidationContext context = OidcRequestContext.getHolder().getOIDCValidationContext();
        return context.getClaims(SELVBETJENING).getClaimSet().getSubject();
    }

}
