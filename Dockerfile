FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Configuración UTF-8
ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen final
FROM amazoncorretto:21.0.4-alpine3.18
WORKDIR /app
COPY --from=build /app/target/SpringBootReservation-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

