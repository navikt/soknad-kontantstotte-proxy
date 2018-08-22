package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

import no.nav.kontantstotte.proxy.innsending.dokument.domain.Søknad;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class DokmotKontantstotteXMLKonvoluttGeneratorTest {

    private DokmotKontantstotteXMLKonvoluttGenerator generator = new DokmotKontantstotteXMLKonvoluttGenerator();

    @Test
    public void that_xml_konverteres_korrekt() {

        String xml = generator.toXML(new Søknad("12345678901", "test".getBytes()));

        // only verifies the most interesting parts of the xml. Timestamps and callIds varies, so cannot verify against a reference xml
        assertThat(xml, containsString("hoveddokument"));
        assertThat(xml, containsString("<ident>12345678901</ident>"));
        assertThat(xml, containsString("<dokumenttypeId>I000072</dokumenttypeId>"));
        assertThat(xml, containsString("<arkivfiltype>PDFA</arkivfiltype>"));
        assertThat(xml, containsString("<variantformat>ARKIV</variantformat>"));
        assertThat(xml, containsString("<behandlingstema>ab0050</behandlingstema>"));
        assertThat(xml, containsString("<dokument>dGVzdA==</dokument>"));
    }

}
