package no.nav.kontantstotte.proxy.config;

import no.nav.kontantstotte.proxy.api.rest.mottak.SoknadMottakResource;
import no.nav.kontantstotte.proxy.api.rest.StatusResource;
import no.nav.security.oidc.jaxrs.OidcContainerRequestFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class RestConfiguration extends ResourceConfig {


    public RestConfiguration() {

        register(JacksonFeature.class);
        //Filter
        register(OidcContainerRequestFilter.class);
        // Resources
        register(StatusResource.class);
        register(SoknadMottakResource.class);

    }

}
