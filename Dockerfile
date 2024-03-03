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

#FROM maven:3.8.3-openjdk-17 AS build
#VOLUME /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

#FROM maven:3.8.3-openjdk-17 AS build
#COPY src /home/app/src
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/home/app/target/spring_rest_docker.jar"]