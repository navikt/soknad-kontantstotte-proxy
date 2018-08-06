package no.nav.kontantstotte.proxy.domain;

public class Person {
    private final String fornavn;
    private final String diskresjonskode;


    public Person(Builder builder) {

        this.fornavn = builder.fornavn;
        this.diskresjonskode = builder.diskresjonskode;
    }

    public String getFornavn() {
        return fornavn;
    }

    public String getDiskresjonskode() { return diskresjonskode; }


    public static class Builder {
        private String fornavn;
        private String diskresjonskode;


        public Builder fornavn(String fornavn) {
            this.fornavn = fornavn;
            return this;
        }

        public Builder diskresjonskode(String diskresjonskode) {
            this.diskresjonskode = diskresjonskode;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
