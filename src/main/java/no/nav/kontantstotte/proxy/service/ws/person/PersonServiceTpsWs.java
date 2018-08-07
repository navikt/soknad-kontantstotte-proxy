package no.nav.kontantstotte.proxy.service.ws.person;

import no.nav.kontantstotte.proxy.domain.Person;
import no.nav.kontantstotte.proxy.domain.PersonService;
import no.nav.kontantstotte.proxy.service.ServiceException;
import no.nav.kontantstotte.proxy.service.SikkerhetsbegrensningExeption;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentPersonPersonIkkeFunnet;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentPersonSikkerhetsbegrensning;
import no.nav.tjeneste.virksomhet.person.v3.binding.PersonV3;
import no.nav.tjeneste.virksomhet.person.v3.meldinger.HentPersonRequest;
import no.nav.tjeneste.virksomhet.person.v3.meldinger.HentPersonResponse;

public class PersonServiceTpsWs implements PersonService {

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
        HentPersonRequest request = RequestUtils.request(fnr);
        try {
            HentPersonResponse hentPersonResponse = this.personV3.hentPerson(request);
            Person person = PersonMapper.person(hentPersonResponse.getPerson());
            if (person.getDiskresjonskode().isPresent()) {
                throw new SikkerhetsbegrensningExeption("Ikke tilgang til å hente personinformasjon");
            }
            return person;
        } catch (HentPersonSikkerhetsbegrensning | HentPersonPersonIkkeFunnet e) {
            throw new ServiceException(e);
        }
    }
}
