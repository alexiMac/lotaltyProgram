#
# Build stage
#
# Use a base image with Java 17
FROM openjdk:17
#FROM maven:3.8.3-openjdk-17 AS build
# Copy the JAR package into the image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# Expose the application port
EXPOSE 8080
# Run the App
ENTRYPOINT ["java", "-jar", "/app.jar"]
