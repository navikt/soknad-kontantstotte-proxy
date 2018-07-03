package no.nav.kontantstotte.proxy.domain;

public class Person {
    private String fornavn;

    public Person(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getFornavn() {
        return fornavn;
    }

    public static class Builder {
        private String fornavn;

        public Builder fornavn(String fornavn) {
            this.fornavn = fornavn;
            return this;
        }

        public Person build() {
            return new Person(this.fornavn);
        }
    }
}
