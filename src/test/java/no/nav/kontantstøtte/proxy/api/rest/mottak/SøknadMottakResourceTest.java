package no.nav.kontantstøtte.proxy.api.rest.mottak;

import no.nav.kontantstøtte.proxy.innsending.dokument.domain.Søknad;
import no.nav.kontantstøtte.proxy.innsending.dokument.domain.SøknadSender;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

public class SøknadMottakResourceTest {

    private SøknadSender søknadSender = mock(SøknadSender.class);

    private JerseyTest jerseyTest;

    @Before
    public void setUp() throws Exception {

        jerseyTest = new JerseyTest() {
            @Override
            protected Application configure() {
                forceSet(TestProperties.CONTAINER_PORT, "0");

                return new ResourceConfig()
                        .register(new SøknadMottakResource(søknadSender))
                        .register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO, LoggingFeature.DEFAULT_VERBOSITY, 10000))
                        .property("contextConfig", new AnnotationConfigApplicationContext());
            }

        };

        jerseyTest.setUp();
    }

    @Test
    public void at_motta_søknad_returnerer_ok() {

        Response response = jerseyTest.target("soknad").request().post(Entity.json(new Søknad("", "".getBytes())));

        assertThat(response.getStatus(), is(equalTo(OK.getStatusCode())));
    }
}
