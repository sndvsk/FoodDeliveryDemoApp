FROM openjdk:17-jdk

COPY . /app

COPY /target/foodDeliveryDemoApp-1.0-SNAPSHOT.jar /app/foodDeliveryDemoApp.jar

WORKDIR /app

EXPOSE 8080

CMD java -jar foodDeliveryDemoApp.jar