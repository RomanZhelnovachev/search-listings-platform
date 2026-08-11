FROM maven:3.9-eclipse-temurin-17 AS build
LABEL authors="RomanZhelnovachev"

WORKDIR /app

COPY pom.xml .
COPY listing-service/pom.xml listing-service/pom.xml

RUN mvn dependency:go-offline

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/listing-service/target/listing-service-1.0.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]