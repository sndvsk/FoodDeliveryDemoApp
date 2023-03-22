package com.example.FoodDeliveryDemoApp;

import com.example.FoodDeliveryDemoApp.controller.FeeRuleController;
import com.example.FoodDeliveryDemoApp.service.deliveryFee.DeliveryFeeServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.airTemperatureRule.ExtraFeeAirTemperatureRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.weatherPhenomenonRule.ExtraFeeWeatherPhenomenonRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.windSpeedRule.ExtraFeeWindSpeedRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.regionalBaseFee.RegionalBaseFeeRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.weatherData.WeatherDataServiceImpl;
import com.example.FoodDeliveryDemoApp.controller.DeliveryFeeController;
import com.example.FoodDeliveryDemoApp.controller.WeatherDataController;
import com.example.FoodDeliveryDemoApp.controller.WeatherDataFromEEAController;
import com.example.FoodDeliveryDemoApp.service.externalWeatherData.ExternalWeatherDataServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FoodDeliveryDemoAppApplicationTests {

	@Autowired
	private DeliveryFeeController deliveryFeeController;

	@Autowired
	private FeeRuleController feeRuleController;

	@Autowired
	private WeatherDataController weatherDataController;

	@Autowired
	private WeatherDataFromEEAController weatherDataFromEEAController;

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


	@Test
	void contextLoads() {
		assertThat(deliveryFeeController).isNotNull();
		assertThat(feeRuleController).isNotNull();
		assertThat(weatherDataController).isNotNull();
		assertThat(weatherDataFromEEAController).isNotNull();
		assertThat(deliveryFeeService).isNotNull();
		assertThat(externalWeatherDataService).isNotNull();
		assertThat(airTemperatureRuleService).isNotNull();
		assertThat(weatherPhenomenonRuleService).isNotNull();
		assertThat(windSpeedRuleService).isNotNull();
		assertThat(regionalBaseFeeRuleService).isNotNull();
		assertThat(weatherDataService).isNotNull();

	}

}
