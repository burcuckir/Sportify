
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

COPY ./infrastructure/pom.xml infrastructure/
COPY ./infrastructure/src/ infrastructure/src/

WORKDIR /app/infrastructure
RUN mvn clean install -DskipTests

WORKDIR /app
COPY ./job-scheduler/pom.xml job-scheduler/
COPY ./job-scheduler/src/ job-scheduler/src/

WORKDIR /app/job-scheduler
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=build /app/job-scheduler/target/*.jar ./app.jar
COPY ./job-scheduler/wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
ENTRYPOINT ["/app/wait-for-it.sh", "http://reservation-api:8082/health", "--timeout=60", "--", "java", "-jar", "app.jar"]
