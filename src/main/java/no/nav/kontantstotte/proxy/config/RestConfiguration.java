package no.nav.kontantstotte.proxy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import no.nav.kontantstotte.proxy.api.rest.exceptionmapper.*;
import no.nav.kontantstotte.proxy.api.rest.mottak.SoknadMottakResource;
import no.nav.security.oidc.jaxrs.OidcContainerRequestFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ext.ContextResolver;


public class RestConfiguration extends ResourceConfig {

    public RestConfiguration() {
        register(JacksonFeature.class);
        register(objectMapperContextResolver());
        //Filter
        register(OidcContainerRequestFilter.class);
        // Exception handlers
        register(DokmotQueueUnavailableExceptionMapper.class);
        register(ExceptionLogger.class);
        // Resources
        register(SoknadMottakResource.class);
    }

    private ContextResolver<ObjectMapper> objectMapperContextResolver() {
        return new ContextResolver<ObjectMapper>() {
            @Override
            public ObjectMapper getContext(Class<?> type) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                return objectMapper;
            }
        };
    }

}
