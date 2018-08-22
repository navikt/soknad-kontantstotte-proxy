package no.nav.kontantstøtte.proxy.api.rest;

import no.nav.kontantstøtte.proxy.config.ApplicationConfig;
import no.nav.security.oidc.test.support.spring.TokenGeneratorConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { ApplicationConfig.class, TokenGeneratorConfiguration.class})
public class StatusResourceTest {

    @Value("${local.server.port}")
    private int port;

    @Value("${spring.jersey.application-path:}")
    private String contextPath;

    @Test
    public void isAlive() {
        WebTarget target = ClientBuilder.newClient().target("http://localhost:" + port + contextPath);
        Response response = target.path("status/isAlive")
                .request()
                .get();

        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
    }

}
