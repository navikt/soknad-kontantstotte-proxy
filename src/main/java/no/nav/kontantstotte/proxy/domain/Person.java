package no.nav.kontantstotte.proxy.domain;

import java.util.List;

public class Person {
    private String fornavn;
    private List<Barn> barn;

    public Person(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getFornavn() {
        return fornavn;
    }

    public static class Builder {
        private String fornavn;
        private List<Barn> barn;

        public Builder fornavn(String fornavn) {
            this.fornavn = fornavn;
            return this;
        }

        public Builder barn(List<Barn> barn) {
            this.barn = barn;
            return this;
        }

        public Person build() {
            return new Person(this.fornavn);
        }
    }
}
