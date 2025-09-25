# 1. Usa immagine ufficiale Java 17 come base
FROM eclipse-temurin:17-jdk-jammy

# 2. Imposta la working directory
WORKDIR /app

# 3. Copia i file Gradle e le sorgenti
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src ./src

# 4. Build del progetto
RUN ./gradlew build -x test

# 5. Espone la porta 8080
EXPOSE 8080

# 6. Comando per avviare l'app
CMD ["java", "-jar", "build/libs/demo-0.0.1-SNAPSHOT.jar"]
