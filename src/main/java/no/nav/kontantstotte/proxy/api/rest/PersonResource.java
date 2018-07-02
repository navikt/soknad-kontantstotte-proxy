package no.nav.kontantstotte.proxy.api.rest;

import no.nav.kontantstotte.proxy.domain.Person;
import no.nav.kontantstotte.proxy.domain.PersonService;
import no.nav.kontantstotte.proxy.service.ServiceException;
import no.nav.security.oidc.api.ProtectedWithClaims;
import no.nav.security.oidc.api.Unprotected;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("person")
@ProtectedWithClaims(issuer = "selvbetjening", claimMap = { "acr=Level4" })
public class PersonResource {

    private final PersonService personService;

    @Inject
    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Person hentPerson() throws ServiceException {
        return personService.hentPersonInfo("fnr");
    }

    @GET
    @Unprotected
    @Path("ping")
    public String ping() {
        personService.ping();
        return "Personservice OK";
    }
}
