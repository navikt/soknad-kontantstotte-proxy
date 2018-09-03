package no.nav.kontantstotte.proxy.api.rest.mottak;

import com.nimbusds.jwt.SignedJWT;
import no.nav.kontantstotte.proxy.config.ApplicationConfig;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.Soknad;
import no.nav.security.oidc.OIDCConstants;
import no.nav.security.oidc.test.support.JwtTokenGenerator;
import no.nav.security.oidc.test.support.spring.TokenGeneratorConfiguration;
import org.hamcrest.core.Is;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
        Response response = send_soknad("12345678911");
        assertThat(response.getStatus(), Is.is(equalTo(Response.Status.OK.getStatusCode())));
    }

    @Test
    public void at_motta_soknad_returnerer_forbidden() {
        Response response = send_soknad("12345678912");
        assertThat(response.getStatus(), Is.is(equalTo(Response.Status.FORBIDDEN.getStatusCode())));
    }

    private Response send_soknad(String soknadFnr) {
        WebTarget target = ClientBuilder.newClient().target("http://localhost:" + port + contextPath);
        SignedJWT signedJWT = JwtTokenGenerator.createSignedJWT("12345678911");
        return target.path("/soknad")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(OIDCConstants.AUTHORIZATION_HEADER, "Bearer " + signedJWT.serialize())
                .buildPost(Entity.json(new Soknad(soknadFnr, "".getBytes())))
                .invoke();
    }
}
