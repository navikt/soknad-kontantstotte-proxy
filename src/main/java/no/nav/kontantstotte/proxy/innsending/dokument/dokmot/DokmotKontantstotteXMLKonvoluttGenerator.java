package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

import no.nav.kontantstotte.proxy.innsending.dokument.dokmot.conversion.Jaxb;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.Soknad;
import no.nav.melding.virksomhet.dokumentforsendelse.v1.*;
import no.nav.servlet.callid.CallId;

import javax.xml.bind.JAXBContext;
import java.time.LocalDateTime;
import java.util.Collections;

class DokmotKontantstotteXMLKonvoluttGenerator {

    private static final String TEMA = "KON";

    private static final String BEHANDLINGSTEMA = "ab0050";

    private static final String KANAL = "NAV_NO";

    private static final String KONTANT_STOTTE_DOKUMENT_TYPE_ID = "I000072";

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
                .withDokumenttypeId(KONTANT_STOTTE_DOKUMENT_TYPE_ID)
                .withDokumentinnholdListe(Collections.singletonList(hovedskjemaInnhold));
    }

}
