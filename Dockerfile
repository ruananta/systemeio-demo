FROM maven:3.8.5-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /app/target/systemeio-0.0.1-SNAPSHOT.jar /app/systemeio.jar
ENTRYPOINT ["java", "-jar", "/app/systemeio.jar"]