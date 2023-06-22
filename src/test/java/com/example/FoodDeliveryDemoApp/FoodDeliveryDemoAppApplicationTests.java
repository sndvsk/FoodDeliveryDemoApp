package com.example.FoodDeliveryDemoApp;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.controller.FeeRuleController;
import com.example.FoodDeliveryDemoApp.scheduler.WeatherDataTask;
import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.service.DeliveryFeeServiceImpl;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.service.extraFee.airTemperatureRule.ExtraFeeAirTemperatureRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.service.extraFee.weatherPhenomenonRule.ExtraFeeWeatherPhenomenonRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.service.extraFee.windSpeedRule.ExtraFeeWindSpeedRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.service.regionalBaseFee.RegionalBaseFeeRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.service.WeatherDataServiceImpl;
import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.controller.DeliveryFeeController;
import com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.controller.WeatherDataController;
import com.example.FoodDeliveryDemoApp.component.weatherItems.externalWeatherData.controller.ExternalWeatherDataController;
import com.example.FoodDeliveryDemoApp.component.weatherItems.externalWeatherData.service.ExternalWeatherDataServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest
class FoodDeliveryDemoAppApplicationTests {

	@Autowired
	private DeliveryFeeController deliveryFeeController;

	@Autowired
	private FeeRuleController feeRuleController;

	@Autowired
	private WeatherDataController weatherDataController;

	@Autowired
	private ExternalWeatherDataController externalWeatherDataController;

	@Autowired
	private WeatherDataTask weatherDataTask;

	@Autowired
	private DeliveryFeeServiceImpl deliveryFeeService;

	@Autowired
	private ExternalWeatherDataServiceImpl externalWeatherDataService;

	@Autowired
	private ExtraFeeAirTemperatureRuleServiceImpl airTemperatureRuleService;

	@Autowired
	private ExtraFeeWeatherPhenomenonRuleServiceImpl weatherPhenomenonRuleService;

	@Autowired
	private ExtraFeeWindSpeedRuleServiceImpl windSpeedRuleService;

	@Autowired
	private RegionalBaseFeeRuleServiceImpl regionalBaseFeeRuleService;

	@Autowired
	private WeatherDataServiceImpl weatherDataService;

	@Autowired
	private FoodDeliveryDemoAppApplication application;


	@Test
	void contextLoads() {
		assertThat(deliveryFeeController).isNotNull();
		assertThat(externalWeatherDataController).isNotNull();
		assertThat(feeRuleController).isNotNull();
		assertThat(weatherDataController).isNotNull();

		assertThat(weatherDataTask).isNotNull();

		assertThat(deliveryFeeService).isNotNull();
		assertThat(externalWeatherDataService).isNotNull();
		assertThat(airTemperatureRuleService).isNotNull();
		assertThat(weatherPhenomenonRuleService).isNotNull();
		assertThat(windSpeedRuleService).isNotNull();
		assertThat(regionalBaseFeeRuleService).isNotNull();
		assertThat(weatherDataService).isNotNull();

		assertThat(application).isNotNull();
	}

}
