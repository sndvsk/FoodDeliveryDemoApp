# We are using a multi-stage build to keep our final image clean and minimal
# Start with a builder image for Maven
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app

# Copy the project's pom.xml and source code to the docker image
COPY pom.xml .
COPY src ./src
# Copy SQL files
COPY src/main/resources/sql ./sql
COPY src/main/resources/load_data.sh ./sql

# Run Maven to build the application
RUN mvn clean package -P no-unit-tests

# Now, start with a base image for Java 17 for our final image
FROM openjdk:17-jdk

# Create a directory for our application
RUN mkdir /app


# Copy the jar file from the builder image
COPY --from=builder /app/target/foodDeliveryDemoApp-1.0-SNAPSHOT.jar /app/foodDeliveryDemoApp.jar
# Copy SQL files
COPY --from=builder /app/sql /app/sql

# Set the application's base path as a working directory
WORKDIR /app

# Expose the port
EXPOSE 8080

# Command to run the application
CMD java -jar foodDeliveryDemoApp.jar
