package no.nav.kontantstotte.proxy.domain;

import java.time.LocalDate;

public class Barn {
    private final String fornavn;
    private final String etternavn;
    private final String fødselsnummer;
    private final LocalDate fødselsdato;

    public Barn(String fornavn, String etternavn, String fødselsnummer, LocalDate fødselsdato) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.fødselsnummer = fødselsnummer;
        this.fødselsdato = fødselsdato;
    }

    public String getFornavn() {
        return fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public String getFødselsnummer() {
        return fødselsnummer;
    }

    public LocalDate getFødselsdato() {
        return fødselsdato;
    }

    public static class Builder {
        private String fornavn;
        private String etternavn;
        private LocalDate fødselsdato;
        private String fødselsnummer;

        public Builder fornavn(String fornavn) {
            this.fornavn = fornavn;
            return this;
        }

        public Builder etternavn(String etternavn) {
            this.etternavn = etternavn;
            return this;
        }

        public Builder fødselsnummer(String fødselsnummer) {
            this.fødselsnummer = fødselsnummer;
            return this;
        }

        public Builder fødselsdato(LocalDate fødselsdato) {
            this.fødselsdato = fødselsdato;
            return this;
        }

        public Barn build() {
            return new Barn(
                    this.fornavn,
                    this.etternavn,
                    this.fødselsnummer,
                    this.fødselsdato
            );
        }
    }

}
