package com.example.FoodDeliveryDemoApp;

import com.example.FoodDeliveryDemoApp.service.DeliveryFee.DeliveryFeeServiceImpl;
import com.example.FoodDeliveryDemoApp.service.WeatherData.WeatherDataServiceImpl;
import com.example.FoodDeliveryDemoApp.controller.DeliveryFeeController;
import com.example.FoodDeliveryDemoApp.controller.WeatherDataController;
import com.example.FoodDeliveryDemoApp.controller.WeatherDataFromEEAController;
import com.example.FoodDeliveryDemoApp.service.ExternalWeatherData.ExternalWeatherDataServiceImpl;
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
	private DeliveryFeeServiceImpl deliveryFeeService;

	@Autowired
	private WeatherDataServiceImpl weatherDataService;

	@Autowired
	private ExternalWeatherDataServiceImpl externalWeatherDataService;

	@Test
	void contextLoads() {
		assertThat(deliveryFeeController).isNotNull();
		assertThat(weatherDataController).isNotNull();
		assertThat(weatherDataFromEEAController).isNotNull();
		assertThat(deliveryFeeService).isNotNull();
		assertThat(weatherDataService).isNotNull();
		assertThat(externalWeatherDataService).isNotNull();

	}

}
