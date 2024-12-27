
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

COPY ./infrastructure/pom.xml infrastructure/
COPY ./infrastructure/src/ infrastructure/src/

WORKDIR /app/infrastructure
RUN mvn clean install -DskipTests

WORKDIR /app
COPY ./user-api/pom.xml user-api/
COPY ./user-api/src/ user-api/src/

WORKDIR /app/user-api
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=build /app/user-api/target/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]