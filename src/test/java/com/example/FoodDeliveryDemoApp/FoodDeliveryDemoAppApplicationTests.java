package com.example.FoodDeliveryDemoApp;

import com.example.FoodDeliveryDemoApp.component.DeliveryFeeComponent;
import com.example.FoodDeliveryDemoApp.component.WeatherDataComponent;
import com.example.FoodDeliveryDemoApp.controller.DeliveryFeeController;
import com.example.FoodDeliveryDemoApp.controller.WeatherDataController;
import com.example.FoodDeliveryDemoApp.controller.WeatherDataFromEEAController;
import com.example.FoodDeliveryDemoApp.service.WeatherDataServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FoodDeliveryDemoAppApplicationTests {

	@Autowired
	private DeliveryFeeController deliveryFeeController;

	@Autowired
	private WeatherDataController weatherDataController;

	@Autowired
	private WeatherDataFromEEAController weatherDataFromEEAController;

	@Autowired
	private DeliveryFeeComponent deliveryFeeComponent;

	@Autowired
	private WeatherDataComponent weatherDataComponent;

	@Autowired
	private WeatherDataServiceImpl weatherDataService;

	@Test
	void contextLoads() {
		assertThat(deliveryFeeController).isNotNull();
		assertThat(weatherDataController).isNotNull();
		assertThat(weatherDataFromEEAController).isNotNull();
		assertThat(deliveryFeeComponent).isNotNull();
		assertThat(weatherDataComponent).isNotNull();
		assertThat(weatherDataService).isNotNull();

	}

}
