# FoodDeliveryDemoApp (API)

## About this project

This project is a technical task. \
The objective of the task is to develop a sub-functionality(API) of the food delivery application, which
calculates the delivery fee for food couriers based on regional base fee, vehicle type, and weather
conditions (extra fees).

## Prerequisites

* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
  
  ```sh
  https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
  ```

* [Maven](https://maven.apache.org/download.cgi)
  
  ```sh
  https://maven.apache.org/download.cgi
  ```

* [Docker](https://www.docker.com/products/docker-desktop/)

  ```sh
  https://www.docker.com/products/docker-desktop/
  ```

## Build

### Build and run using Maven

1. Download .zip or clone project from GitHub.

2. Unzip and/or go to project location in __terminal__.

3. Build the project using maven
   ```sh
   mvn clean package
   ```
   
4. Run the project
   ```sh
   java -jar ./target/foodDeliveryDemoApp-1.0-SNAPSHOT.jar
   ```
   
5. Now you can use the project.

6. Use [Swagger UI](http://localhost:8080/swagger-ui/index.html) to test the endpoints.

P.S. Scheduled task gets data right after start of the application and then every hour at 15 minutes.
If you want to get latest data when you want - use __/get-weather-from-eea__ endpoint and use POST method.

### Build using Maven and run using Docker

1. Download .zip or clone project from GitHub.

2. Unzip and/or go to project location in __terminal__.

3. Build the project using maven
   ```sh
   mvn clean package
   ```

4. Run the project
   ```sh
   docker compose up
   ```
   
N.B!
Use code below, if project was updated!
   ```sh
   docker-compose up --build
   ```



## How to use

Use [Swagger UI](http://localhost:8080/swagger-ui/index.html) to test the endpoints.

![swagger ui look](src/main/resources/misc/png/swagger_look.png)

Swagger UI is powered by SpringDoc OpenAPI (v2.0.4) [springdoc-openapi v2.0.4](https://springdoc.org/v2/)

Postman can also be used, as well as CURL etc. \
Most of the validation supports exception handling for empty parameters.

## Tests

There are unit tests and integration tests. Each of them use different environment(database).

By default, while building the project using ```mvn clean package``` only unit tests run automatically.
If you want to run the integration tests, refer to the instruction below.

### How to run from maven

* Integration tests

  - Run integration tests separately
    ```sh
    mvn test -Dtest=**IT.java
    ```
  - Run as verify
    ```sh
    mvn clean verify
    ```
  - Run as install
    ```sh
    mvn clean install
    ```
    
    
- Unit tests 
  
  - Build the project using maven (unit tests run automatically)
     ```sh
     mvn clean package
     ```
    
  - To run only the application unit tests just type the following command:
    ```sh
    mvn test
    ```
    
  - To skip unit tests via command line during compilation just add the parameter `-P no-tests`:
    ```sh
    mvn clean package -P no-unit-tests
    ```
  

P.S. There are sometimes some problems with WeatherDataControllerIT integration tests.

If it didn't pass after first try, run it as provided below and test should pass.
  ```sh
   mvn test -Dtest=WeatherDataControllerIT.java
   ```

### Coverage

1. All methods in controllers are covered by tests.
![all test coverage](src/main/resources/misc/png/all_test_coverage.png)
   

2. Most of the service methods and lines are covered by tests.
![service test coverage](src/main/resources/misc/png/service_test_coverage.png)
   

## Objectives

1. Database for storing and manipulating data ✅
2. Configurable scheduled task for importing weather data (CronJob) ✅
3. Functionality to calculate delivery fee ✅
4. REST interface, which enables to request of the delivery fee according to input parameters ✅
   
### Bonuses

1. Bonus 1. CRUD REST interface for regional base fees and extra fees for weather conditions. ✅
2. Bonus 2. Datetime in REST interface request. ✅

### Additional

There are some more additions in my solution that were not listed in the task.

## TODOS

1. _>90%_ test coverage in services.
2. Make integration test environment better. (RepositoryInitializer, DatabaseCleanupTestExecutionListener, @DataJpaTest)
