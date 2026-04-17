FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY project1/pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY project1/src ./src
RUN mvn -q -DskipTests clean package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
