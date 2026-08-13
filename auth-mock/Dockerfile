FROM eclipse-temurin:17-jdk
LABEL authors="zheln"

WORKDIR /app

COPY auth-mock/target/auth-mock-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]