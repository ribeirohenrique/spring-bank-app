FROM maven:3.8.4-jdk-17-alpine AS build

RUN apk update && \
    apk add --no-cache openjdk17 && \
    mkdir /app

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-alpine3.15

EXPOSE 8080

COPY --from=build /app/target/spring-bank-app-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]