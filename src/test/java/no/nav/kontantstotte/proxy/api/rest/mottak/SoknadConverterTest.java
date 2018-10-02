package no.nav.kontantstotte.proxy.api.rest.mottak;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.Soknad;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SoknadConverterTest {

    private SoknadConverter converter = new SoknadConverter();

    @Test
    @Ignore
    public void at_soknad_uten_innsendt_dato_far_generert_dato() {

        Soknad soknadUtenTimestamp = converter.toSoknad(new SoknadDto("MASKERT_FNR", "pdf".getBytes()));

        assertThat(soknadUtenTimestamp.getInnsendingTimestamp()).isNotNull();

    }

    @Test
    @Ignore
    public void at_soknad_med_innsendt_dato_beholdes() {

        LocalDateTime someDate = LocalDateTime.now().minusDays(10);

        Soknad soknadMedTimestamp = converter.toSoknad(new SoknadDto("MASKERT_FNR", "pdf".getBytes()));

        assertThat(soknadMedTimestamp.getInnsendingTimestamp()).isEqualTo(someDate);
    }

}
