package no.nav.kontantstotte.innsending.dokmot;

import no.nav.kontantstotte.innsending.domene.Soknad;
import no.nav.melding.virksomhet.dokumentforsendelse.v1.*;
import no.nav.servlet.callid.CallId;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class DokmotEngangsstonadXMLKonvoluttGenerator {

    private static final String TEMA = "KON";

    private static final String BEHANDLINGSTEMA = "ab0050";

    private static final String KANAL = "NAV_NO";

    private static final JAXBContext CONTEXT = Jaxb.context(Dokumentforsendelse.class);

    String toXML(Soknad søknad) {

        String ref = CallId.getOrCreate();

        return Jaxb.marshall(CONTEXT, new Dokumentforsendelse()
                .withForsendelsesinformasjon(new Forsendelsesinformasjon()
                        .withKanalreferanseId(ref)
                        .withTema(new Tema().withValue(TEMA))
                        .withMottakskanal(new Mottakskanaler().withValue(KANAL))
                        .withBehandlingstema(new Behandlingstema().withValue(BEHANDLINGSTEMA))
                        .withForsendelseInnsendt(LocalDateTime.now())
                        .withForsendelseMottatt(LocalDateTime.now())
                        .withAvsender(new Person(søknad.getFnr()))
                        .withBruker(new Person(søknad.getFnr())))
                .withHoveddokument(hoveddokument(søknad)));
    }

    private Hoveddokument hoveddokument(Soknad søknad) {
        Dokumentinnhold hovedskjemaInnhold = new Dokumentinnhold()
                .withDokument(søknad.getPdf())
                .withArkivfiltype(new Arkivfiltyper().withValue("PDFA"))
                .withVariantformat(new Variantformater().withValue("ARKIV"));

        return new Hoveddokument()
                .withDokumenttypeId("I000072")
                .withDokumentinnholdListe(Collections.singletonList(hovedskjemaInnhold));
    }

}
