package no.nav.kontantstotte.proxy.api.rest.mottak;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.Soknad;
import org.junit.Test;

import java.time.Instant;
import java.time.Period;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SoknadConverterTest {

    private SoknadConverter converter = new SoknadConverter();

    private final Instant someDate = Instant.now().minus(Period.ofDays(10));

    @Test
    public void at_soknad_uten_innsendt_dato_far_generert_dato() {

        Soknad soknadUtenTimestamp = converter.toSoknad(new SoknadDto("MASKERT_FNR", "pdf".getBytes(), null, null));

        assertThat(soknadUtenTimestamp.getInnsendingsTidspunkt()).isNotNull();

    }

    @Test
    public void at_soknad_med_innsendt_dato_beholdes() {

        Soknad soknadMedTimestamp = converter.toSoknad(new SoknadDto("MASKERT_FNR", "pdf".getBytes(),
                someDate, null));

        assertThat(soknadMedTimestamp.getInnsendingsTidspunkt()).isEqualTo(someDate);
    }

    @Test
    public void at_soknad_konverterer_null_vedlegg_til_tom_liste() {

        Soknad soknad = converter.toSoknad(new SoknadDto("MASKERT_FNR", "pdf".getBytes(), someDate, null));

        assertThat(soknad.getVedlegg()).isNotNull();
        assertThat(soknad.getVedlegg().size()).isEqualTo(0);

    }

    @Test
    public void at_soknad_konverterer_flere_vedlegg() {

        Soknad soknad = converter.toSoknad(new SoknadDto("MASKERT_FNR", "pdf".getBytes(), someDate,
                Arrays.asList(
                        new VedleggDto("Vedlegg".getBytes(), "tittel", "doktype"),
                        new VedleggDto("Vedlegg2".getBytes(), "tittel2", "doktype2"))));

        assertThat(soknad.getVedlegg().size()).isEqualTo(2);

    }

    @Test
    public void at_soknad_konverterer_ett_vedlegg() {

        Soknad soknad = converter.toSoknad(new SoknadDto("MASKERT_FNR", "pdf".getBytes(), someDate,
                Collections.singletonList(
                        new VedleggDto("Vedlegg".getBytes(), "tittel", "doktype"))));

        assertThat(soknad.getVedlegg().size()).isEqualTo(1);

        assertThat(soknad.getVedlegg().get(0).getData()).isEqualTo("Vedlegg".getBytes());
        assertThat(soknad.getVedlegg().get(0).getTittel()).isEqualTo("tittel");
        assertThat(soknad.getVedlegg().get(0).getDokumenttype()).isEqualTo("doktype");

    }


}
