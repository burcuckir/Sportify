
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

COPY ./infrastructure/pom.xml infrastructure/
COPY ./infrastructure/src/ infrastructure/src/

WORKDIR /app/infrastructure
RUN mvn clean install -DskipTests

WORKDIR /app
COPY ./reservation-api/pom.xml reservation-api/
COPY ./reservation-api/src/ reservation-api/src/

WORKDIR /app/reservation-api
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/reservation-api/target/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]