package no.nav.kontantstotte.proxy.service.ws.person;

import no.nav.kontantstotte.proxy.domain.Person;
import no.nav.kontantstotte.proxy.domain.PersonService;
import no.nav.kontantstotte.proxy.service.ServiceException;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentPersonPersonIkkeFunnet;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentPersonSikkerhetsbegrensning;
import no.nav.tjeneste.virksomhet.person.v3.binding.PersonV3;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.Familierelasjon;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.Informasjonsbehov;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.NorskIdent;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.PersonIdent;
import no.nav.tjeneste.virksomhet.person.v3.meldinger.HentPersonRequest;
import no.nav.tjeneste.virksomhet.person.v3.meldinger.HentPersonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static no.nav.kontantstotte.proxy.service.ws.person.PersonMapper.BARN;
import static no.nav.kontantstotte.proxy.service.ws.person.RequestUtils.FNR;

public class PersonServiceTpsWs implements PersonService {

    public static final Logger LOG = LoggerFactory.getLogger(PersonServiceTpsWs.class);

    private final PersonV3 personV3;
    private final PersonV3 healthIndicator;

    public PersonServiceTpsWs(PersonV3 personV3, PersonV3 healthIndicator) {
        this.personV3 = personV3;
        this.healthIndicator = healthIndicator;
    }

    @Override
    public void ping() {
        healthIndicator.ping();
    }

    @Override
    public Person hentPersonInfo(String fnr) throws ServiceException {
        HentPersonRequest request = RequestUtils.request(fnr, Informasjonsbehov.FAMILIERELASJONER);
        HentPersonResponse hentPersonResponse = hentPerson(fnr, request);
        List<no.nav.tjeneste.virksomhet.person.v3.informasjon.Person> barn = barnFor(hentPersonResponse.getPerson());
        return PersonMapper.person(hentPersonResponse.getPerson(), barn);
    }

    private boolean isBarn(Familierelasjon rel) {
        return rel.getTilRolle().getValue().equals(BARN);
    }

    private List<no.nav.tjeneste.virksomhet.person.v3.informasjon.Person> barnFor(no.nav.tjeneste.virksomhet.person.v3.informasjon.Person forelder) {
        PersonIdent id = PersonIdent.class.cast(forelder.getAktoer());
        String idType = id.getIdent().getType().getValue();
        switch (idType) {
            case FNR:
                return forelder.getHarFraRolleI().stream()
                        .filter(this::isBarn)
                        .map(s -> hentBarn(s))
                        .collect(Collectors.toList());
            default:
                throw new IllegalStateException("ID type " + idType + " ikke støttet");
        }
    }

    private no.nav.tjeneste.virksomhet.person.v3.informasjon.Person hentBarn(Familierelasjon rel) throws ServiceException {
        NorskIdent id = PersonIdent.class.cast(rel.getTilPerson().getAktoer()).getIdent();
        if (RequestUtils.isFnr(id)) {
            String fnrBarn = id.getIdent();
            HentPersonResponse hentPersonResponse = hentPerson(fnrBarn, RequestUtils.request(fnrBarn, Informasjonsbehov.FAMILIERELASJONER));
            return hentPersonResponse.getPerson();
        }
        return null;
    }

    private HentPersonResponse hentPerson(String fnr, HentPersonRequest request) throws ServiceException {
        try {
            return personV3.hentPerson(request);
        } catch (HentPersonSikkerhetsbegrensning | HentPersonPersonIkkeFunnet e) {
            LOG.info("Uthenting av {} feilet", fnr);
            throw new ServiceException(e);
        }
    }
}
