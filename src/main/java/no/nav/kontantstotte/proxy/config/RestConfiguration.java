package no.nav.kontantstotte.proxy.config;

import no.finn.unleash.Unleash;
import no.nav.kontantstotte.proxy.api.rest.PersonResource;
import no.nav.kontantstotte.proxy.api.rest.StatusResource;
import no.nav.kontantstotte.proxy.api.rest.exceptionmapper.OIDCUnauthorizedExceptionMapper;
import no.nav.kontantstotte.proxy.api.rest.exceptionmapper.ServiceExceptionMapper;
import no.nav.security.oidc.jaxrs.OidcContainerRequestFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Inject;

public class RestConfiguration extends ResourceConfig {

    @Inject
    public RestConfiguration(Unleash unleash) {

        register(JacksonFeature.class);
        //Filter
        register(OidcContainerRequestFilter.class);
        // Exception Mappers
        register(OIDCUnauthorizedExceptionMapper.class);
        register(ServiceExceptionMapper.class);
        // Resources
        register(StatusResource.class);
        if( unleash.isEnabled("PersonV3") ) {
            register(PersonResource.class);
        }
    }

}
