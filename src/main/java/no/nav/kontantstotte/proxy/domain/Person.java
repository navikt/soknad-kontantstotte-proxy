package no.nav.kontantstotte.proxy.domain;

import java.util.List;

public class Person {
    private final String fornavn;
    private final List<Barn> barn;

    public Person(String fornavn, List<Barn> barn) {
        this.fornavn = fornavn;
        this.barn = barn;
    }

    public String getFornavn() {
        return fornavn;
    }

    public List<Barn> getBarn() {
        return barn;
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
            return new Person(this.fornavn, this.barn);
        }
    }
}
