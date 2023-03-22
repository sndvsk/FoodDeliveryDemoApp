package com.example.FoodDeliveryDemoApp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@OpenAPIDefinition(
		info=@Info(
				title="FoodDeliveryDemoApp API",
				description = """
				<font size='5'>This app has a sub-functionality of the food delivery application, which calculates the delivery fee for food couriers based on regional base fee, vehicle type, and weather conditions.</font>""",
				version = "v1"),
		servers = @Server(url = "http://localhost:8080", description = "Default Server URL")
)
@SpringBootApplication
public class FoodDeliveryDemoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryDemoAppApplication.class, args);
	}

}
