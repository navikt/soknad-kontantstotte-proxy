FROM navikt/java:8

ADD ./VERSION /app/VERSION
COPY ./target/søknad-kontantstotte-proxy.jar "/app/app.jar"
