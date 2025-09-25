# Blue Innovation DEMO - Spring Boot

## Requisiti
- Java 17+
- Gradle
- Database in memoria H2

## Build e avvio del server
Esegui questi comandi nel terminale:
- ./gradlew build
- ./gradlew bootRun

## Eseguire tutti i test
Esegui:
- ./gradlew test

## Console H2
Aprire nel browser:
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- User: sa
- Password: (lasciare vuoto)

## Build e avvio con Docker
- docker build -t demo-springboot .
- docker run -p 8080:8080 demo-springboot
