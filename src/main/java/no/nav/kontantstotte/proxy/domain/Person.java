package no.nav.kontantstotte.proxy.domain;

public class Person {
    private String fornavn;

    public Person(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getFornavn() {
        return fornavn;
    }

    public static class PersonBuilder {
        private String fornavn;

        public PersonBuilder fornavn(String fornavn) {
            this.fornavn = fornavn;
            return this;
        }

        public Person build() {
            return new Person(this.fornavn);
        }
    }
}
