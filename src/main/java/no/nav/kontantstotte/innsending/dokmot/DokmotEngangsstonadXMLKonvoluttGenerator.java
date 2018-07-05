package no.nav.kontantstotte.innsending.dokmot;

import no.nav.kontantstotte.innsending.domene.Soknad;
import no.nav.melding.virksomhet.dokumentforsendelse.v1.*;
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

//    private final DokmotEngangsstønadXMLGenerator søknadGenerator;
//
//    public DokmotEngangsstønadXMLKonvoluttGenerator(DokmotEngangsstønadXMLGenerator generator) {
//        this.søknadGenerator = Objects.requireNonNull(generator);
//    }
//
//    public String toXML(Søknad søknad, no.nav.foreldrepenger.mottak.domain.felles.Person søker, String ref) {
//        return Jaxb.marshall(CONTEXT, dokmotModelFra(søknad, søker, ref));
//    }
    String toXML(Soknad søknad) {

        return Jaxb.marshall(CONTEXT, new Dokumentforsendelse()
                .withForsendelsesinformasjon(new Forsendelsesinformasjon()
                        .withKanalreferanseId("anything")
                        .withTema(new Tema().withValue(TEMA))
                        .withMottakskanal(new Mottakskanaler().withValue(KANAL))
                        .withBehandlingstema(new Behandlingstema().withValue(BEHANDLINGSTEMA))
                        .withForsendelseInnsendt(LocalDateTime.now())
                        .withForsendelseMottatt(LocalDateTime.now())
                        .withAvsender(new Person(søknad.getFnr()))
                        .withBruker(new Person(søknad.getFnr())))
                .withHoveddokument(hoveddokument(søknad)));
//                .withVedleggListe(dokmotVedleggListe(søknad));
    }
//
//    public Dokumentforsendelse dokmotModelFra(Søknad søknad, no.nav.foreldrepenger.mottak.domain.felles.Person søker,
//            String ref) {
//        return dokumentForsendelseFra(søknad, søker, ref);
//    }
//
//    public String toSøknadsXML(Søknad søknad, no.nav.foreldrepenger.mottak.domain.felles.Person søker) {
//        return søknadGenerator.toXML(søknad, søker);
//    }
//
//    private Dokumentforsendelse dokumentForsendelseFra(Søknad søknad,
//            no.nav.foreldrepenger.mottak.domain.felles.Person søker, String ref) {
//        return new Dokumentforsendelse()
//                .withForsendelsesinformasjon(new Forsendelsesinformasjon()
//                        .withKanalreferanseId(ref)
//                        .withTema(new Tema().withValue(TEMA))
//                        .withMottakskanal(new Mottakskanaler().withValue(KANAL))
//                        .withBehandlingstema(new Behandlingstema().withValue(BEHANDLINGSTEMA))
//                        .withForsendelseInnsendt(LocalDateTime.now())
//                        .withForsendelseMottatt(søknad.getMottattdato())
//                        .withAvsender(new Person(søker.fnr.getFnr()))
//                        .withBruker(new Person(søker.fnr.getFnr())))
//                .withHoveddokument(hoveddokument(søknad, søker))
//                .withVedleggListe(dokmotVedleggListe(søknad));
//    }
//
    private Hoveddokument hoveddokument(Soknad søknad) {
        Dokumentinnhold hovedskjemaInnhold = new Dokumentinnhold()
                .withDokument(søknad.getPdf())
                .withArkivfiltype(new Arkivfiltyper().withValue("PDFA"))
                .withVariantformat(new Variantformater().withValue("ARKIV"));
//        Stream<Dokumentinnhold> alternativeRepresentasjonerInnhold = Collections.singletonList(new Dokumentinnhold()
//                .withDokument(søknadGenerator.toXML(søknad, søker).getBytes())
//                .withVariantformat(new Variantformater().withValue(ORIGINAL.name()))
//                .withArkivfiltype(new Arkivfiltyper().withValue(XML.name()))).stream();

        return new Hoveddokument()
//                .withDokumenttypeId(ENGANGSSTØNAD_FØDSEL.id)
                .withDokumentinnholdListe(Collections.singletonList(hovedskjemaInnhold));
    }
//
//    private static List<no.nav.melding.virksomhet.dokumentforsendelse.v1.Vedlegg> dokmotVedleggListe(Søknad søknad) {
//        return Stream.concat(søknad.getPåkrevdeVedlegg().stream(), søknad.getFrivilligeVedlegg().stream())
//                .map(DokmotEngangsstønadXMLKonvoluttGenerator::dokmotVedlegg).collect(toList());
//    }
//
//    private static no.nav.melding.virksomhet.dokumentforsendelse.v1.Vedlegg dokmotVedlegg(Vedlegg vedlegg) {
//
//        return new no.nav.melding.virksomhet.dokumentforsendelse.v1.Vedlegg()
//                .withBrukeroppgittTittel(vedlegg.getMetadata().getBeskrivelse())
//                .withDokumenttypeId(vedlegg.getMetadata().getSkjemanummer().id)
//                .withDokumentinnholdListe(new Dokumentinnhold()
//                        .withVariantformat(new Variantformater().withValue(ARKIV.name()))
//                        .withArkivfiltype(new Arkivfiltyper().withValue(Filtype.PDF.name()))
//                        .withDokument(vedlegg.getVedlegg()));
//    }
//
//    @Override
//    public String toString() {
//        return getClass().getSimpleName() + " [søknadGenerator=" + søknadGenerator + "]";
//    }

}
