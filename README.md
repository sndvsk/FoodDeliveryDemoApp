# FoodDeliveryDemoApp

## About this project
This project is a technical task.

## Prerequisites

* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
  ```sh
  https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
  ```

* [Maven](https://maven.apache.org/download.cgi)
  ```sh
  https://maven.apache.org/download.cgi
  ```

## Build

1. Download .zip or clone project from GitHub.

2. Unzip and/or go to project location in __terminal__.

3. Build the project using maven
   ```sh
   mvn clean package
   ```
4. Run the project
   ```sh
   java -jar ./target/FoodDeliveryDemoApp-1.0-SNAPSHOT.jar
   ```
5. Now you can use the project.

P.S. Scheduled task gets data right after start of the application and then every hour at 15 minutes.
If you want to get latest data when you want use __/get-weather-from-eea__ endpoint.


## How to use

Api documentation and endpoints are provided by [springdoc-openapi v2](https://springdoc.org/v2/)

[Swagger UI](http://localhost:8080/swagger-ui/index.html) of the project.

Here you can use all endpoints for testing the application.