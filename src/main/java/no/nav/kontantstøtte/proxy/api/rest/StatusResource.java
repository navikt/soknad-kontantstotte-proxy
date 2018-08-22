package no.nav.kontantstøtte.proxy.api.rest;

import no.nav.security.oidc.api.Unprotected;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("status")
public class StatusResource {

    @GET
    @Path("isAlive")
    @Unprotected
    public String isAlive() { return "Ok"; }

}
