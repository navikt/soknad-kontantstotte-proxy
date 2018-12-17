package no.nav.kontantstotte.proxy.api.rest.mottak;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nimbusds.jwt.SignedJWT;
import no.nav.kontantstotte.proxy.config.ApplicationConfig;
import no.nav.security.oidc.OIDCConstants;
import no.nav.security.oidc.test.support.JwtTokenGenerator;
import no.nav.security.oidc.test.support.spring.TokenGeneratorConfiguration;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;

import java.util.Collections;
import java.util.List;

import static java.time.Instant.now;
import static java.util.Collections.emptyList;
import static no.nav.kontantstotte.proxy.api.rest.mottak.SoknadMottakResource.MINIMUM_PDF_STORRELSE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { ApplicationConfig.class, TokenGeneratorConfiguration.class })
public class SoknadMottakResourceTest {
    @Value("${local.server.port}")
    private int port;

    @Value("${spring.jersey.application-path}")
    private String contextPath;

    @Test
    public void at_motta_soknad_returnerer_ok() {
        Response response = send_soknad(soknadDto("MASKERT_FNR"));
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void at_motta_soknad_returnerer_forbidden() {
        Response response = send_soknad(soknadDto("MASKERT_FNR2"));
        assertThat(response.getStatus()).isEqualTo(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    public void at_motta_soknad_returnerer_bad_request_pga_liten_pdf() {
        SoknadDto soknadDto = new SoknadDto("MASKERT_FNR", "".getBytes(), now(), emptyList());
        Response response = send_soknad(soknadDto);
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    private SoknadDto soknadDto(String soknadFnr) {
        return new SoknadDto(soknadFnr, new byte[MINIMUM_PDF_STORRELSE], now(), emptyList());
    }

    private Response send_soknad(Object entity) {

        WebTarget target = ClientBuilder.newClient()
                .register(new ContextResolver<ObjectMapper>() {
                    @Override
                    public ObjectMapper getContext(Class<?> type) {
                        return new ObjectMapper()
                                .registerModule(new JavaTimeModule())
                                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    }
                })
                .register(new LoggingFeature())
                .target("http://localhost:" + port + contextPath);
        SignedJWT signedJWT = JwtTokenGenerator.createSignedJWT("MASKERT_FNR");
        return target.path("/soknad")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(OIDCConstants.AUTHORIZATION_HEADER, "Bearer " + signedJWT.serialize())
                .buildPost(Entity.json(entity))
                .invoke();
    }
}
