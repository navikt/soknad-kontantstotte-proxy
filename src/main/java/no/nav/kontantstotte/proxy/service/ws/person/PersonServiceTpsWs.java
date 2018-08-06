package no.nav.kontantstotte.proxy.service.ws.person;

import no.nav.kontantstotte.proxy.domain.Person;
import no.nav.kontantstotte.proxy.domain.PersonService;
import no.nav.kontantstotte.proxy.service.ServiceException;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentPersonPersonIkkeFunnet;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentPersonSikkerhetsbegrensning;
import no.nav.tjeneste.virksomhet.person.v3.binding.PersonV3;
import no.nav.tjeneste.virksomhet.person.v3.meldinger.HentPersonRequest;
import no.nav.tjeneste.virksomhet.person.v3.meldinger.HentPersonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonServiceTpsWs implements PersonService {

    private final PersonV3 personV3;
    private final PersonV3 healthIndicator;
    private static final Logger LOG = LoggerFactory.getLogger(PersonServiceTpsWs.class);

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
        HentPersonRequest request = RequestUtils.request(fnr);
        try {
            HentPersonResponse hentPersonResponse = this.personV3.hentPerson(request);
            LOG.info("Personkall til TPS ok");
            return PersonMapper.person(hentPersonResponse.getPerson());
        } catch (HentPersonSikkerhetsbegrensning | HentPersonPersonIkkeFunnet e) {
            LOG.error("Personkall til TPS gir feil:", e);
            throw new ServiceException(e);
        }
    }
}
