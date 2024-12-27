# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-21 AS build
# Set the working directory
WORKDIR /app
# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline
# Copy the rest of the application code
COPY src/ ./src
# Build the application
RUN mvn clean package -DskipTests
# Stage 2: Run the application
FROM openjdk:21-jdk-slim
# Set the working directory
WORKDIR /app
# Copy the JAR file from the build stage
COPY --from=build /app/target/admin_landsurveys-0.0.1.jar /app/admin_landsurveys.jar
# Expose the port the application will run on
EXPOSE 3500
# Run the Spring Boot application
CMD ["java", "-jar", "/app/admin_landsurveys.jar"]