FROM navikt/java:11-appdynamics

COPY appdynamics.sh /init-scripts/

ADD ./VERSION /app/VERSION
COPY ./target/soknad-kontantstotte-proxy.jar "/app/app.jar"

ENV JAVA_OPTS "${JAVA_OPTS} -Xmx1536m"
