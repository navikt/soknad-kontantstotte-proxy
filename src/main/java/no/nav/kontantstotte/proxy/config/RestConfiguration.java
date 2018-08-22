package no.nav.kontantstotte.proxy.config;

import no.nav.kontantstotte.proxy.api.rest.exceptionmapper.SøknadInnsendingExceptionMapper;
import no.nav.kontantstotte.proxy.api.rest.mottak.SøknadMottakResource;
import no.nav.kontantstotte.proxy.api.rest.oppslag.PersonResource;
import no.nav.kontantstotte.proxy.api.rest.StatusResource;
import no.nav.kontantstotte.proxy.api.rest.exceptionmapper.ServiceExceptionMapper;
import no.nav.kontantstotte.proxy.api.rest.exceptionmapper.SikkerhetsbegrensningExceptionMapper;
import no.nav.security.oidc.jaxrs.OidcContainerRequestFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;


public class RestConfiguration extends ResourceConfig {

    public RestConfiguration() {
        register(JacksonFeature.class);
        //Filter
        register(OidcContainerRequestFilter.class);
        // Exception Mappers
        register(ServiceExceptionMapper.class);
        register(SøknadInnsendingExceptionMapper.class);
        register(SikkerhetsbegrensningExceptionMapper.class);
        // Resources
        register(StatusResource.class);
        register(SøknadMottakResource.class);
        register(PersonResource.class);
    }

}
