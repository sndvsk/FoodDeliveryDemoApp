package com.example.FoodDeliveryDemoApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// TODO
// add AWS, Terraform, Kubernetes, Jenkins
// and maybe Jira or Confluence integration

@EnableScheduling
@SpringBootApplication
public class FoodDeliveryDemoAppApplication {

	private static final Logger logger = LoggerFactory.getLogger(FoodDeliveryDemoAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryDemoAppApplication.class, args);

		logger.info("Application is live. Go to ´http://localhost:8080/swagger-ui/index.html´ to use the application.");
	}

}
