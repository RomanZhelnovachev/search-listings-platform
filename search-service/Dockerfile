FROM maven:3.9-eclipse-temurin-17 AS build
LABEL authors="RomanZhelnovachev"

WORKDIR /app

COPY pom.xml .
COPY search-service/pom.xml search-service/pom.xml
COPY listing-service/pom.xml listing-service/pom.xml
COPY auth-mock/pom.xml auth-mock/pom.xml

RUN mvn dependency:go-offline

COPY . .

RUN mvn clean package -pl search-service -am -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/search-service/target/search-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dcom.sun.management.jmxremote", "-Dcom.sun.management.jmxremote.port=9010", "-Dcom.sun.management.jmxremote.rmi.port=9010", "-Dcom.sun.management.jmxremote.authenticate=false", "-Dcom.sun.management.jmxremote.ssl=false", "-Djava.rmi.server.hostname=localhost", "-jar", "app.jar"]
