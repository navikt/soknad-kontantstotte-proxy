package no.nav.kontantstotte.proxy.innsending.dokument.dokmot;

import no.nav.kontantstotte.proxy.innsending.dokument.dokmot.conversion.Jaxb;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.Soknad;
import no.nav.kontantstotte.proxy.innsending.dokument.domain.SoknadVedlegg;
import no.nav.log.MDCConstants;
import no.nav.melding.virksomhet.dokumentforsendelse.v1.*;
import org.slf4j.MDC;

import javax.xml.bind.JAXBContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;


class DokmotKontantstotteXMLKonvoluttGenerator {

    private static final String TEMA = "KON";

    private static final String BEHANDLINGSTEMA = "ab0084";

    private static final String KANAL = "NAV_NO";

    private static final String KONTANTSTOTTE_DOKUMENT_TYPE_ID = "I000072";

    private static final JAXBContext CONTEXT = Jaxb.context(Dokumentforsendelse.class);

    String toXML(Soknad soknad) {

        String ref = MDC.get(MDCConstants.MDC_CORRELATION_ID);

        LocalDateTime innsendingsTidspunkt = LocalDateTime.ofInstant(soknad.getInnsendingsTidspunkt(), ZoneId.of("Europe/Paris"));
        XMLGregorianCalendar innsendingsTidspunktXML = konverterTilGregorianCalendar(innsendingsTidspunkt);

        return Jaxb.marshall(CONTEXT, new Dokumentforsendelse()
                .withForsendelsesinformasjon(new Forsendelsesinformasjon()
                        .withKanalreferanseId(ref)
                        .withTema(new Tema().withValue(TEMA))
                        .withMottakskanal(new Mottakskanaler().withValue(KANAL))
                        .withBehandlingstema(new Behandlingstema().withValue(BEHANDLINGSTEMA))
                        .withForsendelseInnsendt(innsendingsTidspunktXML)
                        .withForsendelseMottatt(innsendingsTidspunktXML)
                        .withAvsender(new Person(soknad.getFnr()))
                        .withBruker(new Person(soknad.getFnr())))
                .withHoveddokument(hoveddokument(soknad))
                .withVedleggListe(vedleggListe(soknad))
        );
    }

    private List<Vedlegg> vedleggListe(Soknad soknad) {
        return soknad.getVedlegg().stream().map(this::vedlegg).collect(Collectors.toList());

    }

    private Vedlegg vedlegg(SoknadVedlegg soknadVedlegg) {

        Dokumentinnhold innhold = new Dokumentinnhold()
                .withDokument(soknadVedlegg.getData())
                .withArkivfiltype(new Arkivfiltyper().withValue("PDF"))
                .withVariantformat(new Variantformater().withValue("ARKIV"));

        return new Vedlegg()
                .withBrukeroppgittTittel(soknadVedlegg.getTittel())
                .withDokumentinnholdListe(innhold);
    }

    private Hoveddokument hoveddokument(Soknad soknad) {
        Dokumentinnhold hovedskjemaInnhold = new Dokumentinnhold()
                .withDokument(soknad.getPdf())
                .withArkivfiltype(new Arkivfiltyper().withValue("PDFA"))
                .withVariantformat(new Variantformater().withValue("ARKIV"));

        return new Hoveddokument()
                .withDokumenttypeId(KONTANTSTOTTE_DOKUMENT_TYPE_ID)
                .withDokumentinnholdListe(Collections.singletonList(hovedskjemaInnhold));
    }

    private static XMLGregorianCalendar konverterTilGregorianCalendar(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        DatatypeFactory datatypeFactory;
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException(e);
        }

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        return datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
    }
}