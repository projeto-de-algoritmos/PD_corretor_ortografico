FROM eclipse-temurin:17

WORKDIR /app

COPY . .

RUN ./gradlew build

EXPOSE 8080

CMD ["java", "-jar", "./build/libs/SequenceAlignment-0.0.1-SNAPSHOT.jar"]