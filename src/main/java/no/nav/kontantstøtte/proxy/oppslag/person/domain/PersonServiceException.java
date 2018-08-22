package no.nav.kontantstøtte.proxy.oppslag.person.domain;

public class PersonServiceException extends Throwable {
    public PersonServiceException(Exception e) {
        super(e);
    }
}
