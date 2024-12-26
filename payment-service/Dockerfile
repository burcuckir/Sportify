
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

COPY ./infrastructure/pom.xml infrastructure/
COPY ./infrastructure/src/ infrastructure/src/

WORKDIR /app/infrastructure
RUN mvn clean install -DskipTests

WORKDIR /app
COPY ./payment-service/pom.xml payment-service/
COPY ./payment-service/src/ payment-service/src/

WORKDIR /app/payment-service
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=build /app/payment-service/target/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]