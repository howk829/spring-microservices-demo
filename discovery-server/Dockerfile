# Use a base image with OpenJDK
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/discovery-server-0.0.1-SNAPSHOT.jar app.jar

# Expose the default port for the Discovery Server
EXPOSE 8761

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]