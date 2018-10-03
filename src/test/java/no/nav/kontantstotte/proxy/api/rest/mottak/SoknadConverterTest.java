package no.nav.kontantstotte.proxy.api.rest.mottak;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.Soknad;
import org.junit.Test;

import java.time.Instant;
import java.time.Period;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SoknadConverterTest {

    private SoknadConverter converter = new SoknadConverter();

    @Test
    public void at_soknad_uten_innsendt_dato_far_generert_dato() {

        Soknad soknadUtenTimestamp = converter.toSoknad(new SoknadDto("MASKERT_FNR", "pdf".getBytes(), null));

        assertThat(soknadUtenTimestamp.getInnsendingTimestamp()).isNotNull();

    }

    @Test
    public void at_soknad_med_innsendt_dato_beholdes() {

        Instant someDate = Instant.now().minus(Period.ofDays(10));

        Soknad soknadMedTimestamp = converter.toSoknad(new SoknadDto("MASKERT_FNR", "pdf".getBytes(),
                someDate));

        assertThat(soknadMedTimestamp.getInnsendingTimestamp()).isEqualTo(someDate);
    }

}
