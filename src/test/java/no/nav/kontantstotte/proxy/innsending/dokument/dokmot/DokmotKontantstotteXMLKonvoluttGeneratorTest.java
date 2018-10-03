package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.Soknad;
import org.junit.Test;

import java.time.*;

import static java.time.LocalDateTime.now;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class DokmotKontantstotteXMLKonvoluttGeneratorTest {

    private DokmotKontantstotteXMLKonvoluttGenerator generator = new DokmotKontantstotteXMLKonvoluttGenerator();

    @Test
    public void that_xml_konverteres_korrekt() {

        String xml = generator.toXML(new Soknad("MASKERT_FNR", "test".getBytes(),
                LocalDateTime.of(2018, 5, 11, 13, 45, 11, 994000000).atZone(ZoneId.of("Europe/Paris")).toInstant()));

        // only verifies the most interesting parts of the xml. Timestamps and callIds varies, so cannot verify against a reference xml
        assertThat(xml, containsString("hoveddokument"));
        assertThat(xml, containsString("<ident>MASKERT_FNR</ident>"));

        assertThat(xml, containsString("<forsendelseMottatt>2018-05-11T13:45:11.994</forsendelseMottatt>"));
        assertThat(xml, containsString("<forsendelseInnsendt>2018-05-11T13:45:11.994</forsendelseInnsendt>"));
        assertThat(xml, containsString("<dokumenttypeId>I000072</dokumenttypeId>"));
        assertThat(xml, containsString("<arkivfiltype>PDFA</arkivfiltype>"));
        assertThat(xml, containsString("<variantformat>ARKIV</variantformat>"));
        assertThat(xml, containsString("<behandlingstema>ab0084</behandlingstema>"));
        assertThat(xml, containsString("<dokument>dGVzdA==</dokument>"));
    }

}
