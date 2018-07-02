package no.nav.kontantstotte.proxy.service.ws.person;

import no.nav.kontantstotte.proxy.domain.Person;
import no.nav.kontantstotte.proxy.domain.PersonService;
import no.nav.kontantstotte.proxy.service.ServiceException;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentPersonPersonIkkeFunnet;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentPersonSikkerhetsbegrensning;
import no.nav.tjeneste.virksomhet.person.v3.binding.PersonV3;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.Informasjonsbehov;
import no.nav.tjeneste.virksomhet.person.v3.meldinger.HentPersonRequest;
import no.nav.tjeneste.virksomhet.person.v3.meldinger.HentPersonResponse;
import org.apache.commons.lang3.NotImplementedException;

public class PersonServiceTpsWs implements PersonService {


    private final PersonV3 healthIndicator;

    public PersonServiceTpsWs( PersonV3 healthIndicator) {
        this.healthIndicator = healthIndicator;
    }

    @Override
    public void ping() {
        healthIndicator.ping();
    }

    @Override
    public Person hentPersonInfo(String fnr) throws ServiceException {
        throw new NotImplementedException("Ikke implementert");
    }
}
