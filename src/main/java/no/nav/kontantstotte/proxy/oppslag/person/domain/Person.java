package no.nav.kontantstotte.proxy.oppslag.person.domain;

import java.util.Optional;

public class Person {
    private final String fornavn;
    private final String mellomnavn;
    private final String slektsnavn;
    private final String diskresjonskode;
    private final Adresse adresse;

    public Person(Builder builder) {

        this.fornavn = builder.fornavn;
        this.diskresjonskode = builder.diskresjonskode;
        this.mellomnavn = builder.mellomnavn;
        this.slektsnavn = builder.slektsnavn;
        this.adresse = builder.adresse;

    }

    public String getFornavn() {
        return fornavn;
    }

    public Optional<String> getDiskresjonskode() { return Optional.ofNullable(diskresjonskode); }

    public static class Builder {
        private String fornavn;
        private String mellomnavn;
        private String slektsnavn;
        private String diskresjonskode;
        private Adresse adresse;


        public Builder fornavn(String fornavn) {
            this.fornavn = fornavn;
            return this;
        }

        public Builder diskresjonskode(String diskresjonskode) {
            this.diskresjonskode = diskresjonskode;
            return this;
        }

        public Builder mellomnavn(String mellomnavn) {
            this.mellomnavn = mellomnavn;
            return this;
        }

        public Builder slektsnavn(String slektsnavn) {
            this.slektsnavn = slektsnavn;
            return this;
        }

        public Builder boadresse(Adresse adresse) {
            this.adresse = adresse;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
