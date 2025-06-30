# 1 - Build with Maven
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

COPY . .

RUN mvn dependency:go-offline
RUN mvn clean package

# 2 - Final Image
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/engineering-management-katas-api-application/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
