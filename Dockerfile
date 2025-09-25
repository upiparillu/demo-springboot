# 1. Usa un'immagine con Java 17
FROM eclipse-temurin:17-jdk AS build

# 2. Imposta la working dir
WORKDIR /app

# 3. Copia i file di gradle wrapper
COPY gradlew .
COPY gradle gradle

# 4. Copia i file di progetto
COPY build.gradle settings.gradle ./
COPY src src

# 5. Rendi eseguibile gradlew
RUN chmod +x gradlew

# 6. Esegui la build senza test
RUN ./gradlew build -x test

# 7. Stage runtime: usa JRE più leggero
FROM eclipse-temurin:17-jre
WORKDIR /app

# 8. Copia il jar buildato
COPY --from=build /app/build/libs/*.jar app.jar

# 9. Espone la porta
EXPOSE 8080

# 10. Avvia il jar
ENTRYPOINT ["java","-jar","app.jar"]
