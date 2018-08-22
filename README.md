# Lokalt oppsett

For å kjøre opp løsningen lokalt:

## Fra kommandolinje:
```
mvn package && java -jar target/søknad-kontantstotte-proxy.jar
```

## Fra IntelliJ
Kjør [main-metoden](src/test/java/no/nav/kontantstotte/proxy/api/TestLauncher.java)

## Via dockerimaget

Erstatt `unversioned` med versjonen du ønsker å kjøre eller bygg unversioned-taggen ved å kjøre `docker build .`

```
docker login repo.adeo.no:5443
docker run -p 8080:8080 repo.adeo.no:5443/søknad/søknad-kontantstotte-proxy:unversioned
```

Kommandoen tilgjengeliggjør serveren på localhost:8081


## Bygging og publisering

For å bygge imaget, kjør `sh build.sh`. Se `sh build.sh --help` for alternativer.
