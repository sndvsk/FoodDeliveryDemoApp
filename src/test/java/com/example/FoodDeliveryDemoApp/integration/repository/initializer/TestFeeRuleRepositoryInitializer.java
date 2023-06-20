package com.example.FoodDeliveryDemoApp.integration.repository.initializer;

import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.domain.DeliveryFee;
import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.repository.DeliveryFeeRepository;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.RegionalBaseFeeRule;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeAirTemperatureRule;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeWindSpeedRule;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.repository.ExtraFeeAirTemperatureRuleRepository;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.repository.ExtraFeeWeatherPhenomenonRuleRepository;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.repository.ExtraFeeWindSpeedRuleRepository;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.repository.RegionalBaseFeeRuleRepository;
import com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.domain.WeatherData;
import com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.repository.WeatherDataRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("test")
public class TestFeeRuleRepositoryInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RegionalBaseFeeRuleRepository baseFeeRuleRepository;
    private ExtraFeeAirTemperatureRuleRepository airTemperatureRepository;
    private ExtraFeeWindSpeedRuleRepository windSpeedRepository;
    private ExtraFeeWeatherPhenomenonRuleRepository weatherPhenomenonRepository;
    private DeliveryFeeRepository deliveryFeeRepository;
    private WeatherDataRepository weatherDataRepository;

    public TestFeeRuleRepositoryInitializer(RegionalBaseFeeRuleRepository baseFeeRuleRepository, ExtraFeeAirTemperatureRuleRepository airTemperatureRepository, ExtraFeeWindSpeedRuleRepository windSpeedRepository, ExtraFeeWeatherPhenomenonRuleRepository weatherPhenomenonRepository, DeliveryFeeRepository deliveryFeeRepository, WeatherDataRepository weatherDataRepository) {
        this.baseFeeRuleRepository = baseFeeRuleRepository;
        this.airTemperatureRepository = airTemperatureRepository;
        this.windSpeedRepository = windSpeedRepository;
        this.weatherPhenomenonRepository = weatherPhenomenonRepository;
        this.deliveryFeeRepository = deliveryFeeRepository;
        this.weatherDataRepository = weatherDataRepository;
    }

    public TestFeeRuleRepositoryInitializer() {
        // default constructor
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {

        logger.info("Checking if there are regional base fee rules in the repository.");
        long baseFeeCount = baseFeeRuleRepository.count();

        logger.info("Checking if there are air temperature extra fee rules in the repository.");
        long airTemperatureCount = airTemperatureRepository.count();

        logger.info("Checking if there are wind speed extra fee rules in the repository.");
        long windSpeedCount = windSpeedRepository.count();

        logger.info("Checking if there are weather component extra fee rules in the repository.");
        long weatherPhenomenonCount = weatherPhenomenonRepository.count();

        logger.info("Checking if there are delivery fee entries in the repository.");
        long deliveryFeeCount = deliveryFeeRepository.count();

        logger.info("Checking if there are weather data entries in the repository.");
        long weatherDataCount = weatherDataRepository.count();

        if (baseFeeCount == 0) {
            logger.info("No regional base fee rules in the repository. Initializing....");
            List<RegionalBaseFeeRule> baseFeeRules = new ArrayList<>();
            baseFeeRules.add(new RegionalBaseFeeRule("tallinn", 26038L, "car", 4.0));
            baseFeeRules.add(new RegionalBaseFeeRule("tallinn", 26038L, "scooter", 3.5));
            baseFeeRules.add(new RegionalBaseFeeRule("tallinn", 26038L, "bike", 3.0));
            baseFeeRules.add(new RegionalBaseFeeRule("tartu", 26242L, "car", 3.5));
            baseFeeRules.add(new RegionalBaseFeeRule("tartu", 26242L, "scooter", 3.0));
            baseFeeRules.add(new RegionalBaseFeeRule("tartu", 26242L, "bike", 2.5));
            baseFeeRules.add(new RegionalBaseFeeRule("pärnu", 41803L, "car", 3.0));
            baseFeeRules.add(new RegionalBaseFeeRule("pärnu", 41803L, "scooter", 2.5));
            baseFeeRules.add(new RegionalBaseFeeRule("pärnu", 41803L, "bike", 2.0));
            baseFeeRuleRepository.saveAll(baseFeeRules);
            logger.info("Regional base fee rules are added into the repository.");
        }

        if (airTemperatureCount == 0) {
            logger.info("No air temperature extra fee rules in the repository. Initializing....");
            List<ExtraFeeAirTemperatureRule> airTemperatureRules = new ArrayList<>();
            airTemperatureRules.add(new ExtraFeeAirTemperatureRule(-10.1, -20.0, 1.0));
            airTemperatureRules.add(new ExtraFeeAirTemperatureRule(0.0, -10.0, 0.5));
            airTemperatureRules.add(new ExtraFeeAirTemperatureRule(0.1, 60.0, 0.0));
            airTemperatureRepository.saveAll(airTemperatureRules);
            logger.info("Air temperature fee rules are added into the repository.");
        }

        if (windSpeedCount == 0) {
            logger.info("No wind speed  extra fee rules in the repository. Initializing....");
            List<ExtraFeeWindSpeedRule> windSpeedRules = new ArrayList<>();
            windSpeedRules.add(new ExtraFeeWindSpeedRule(20.1, 26.0, -1.0));
            windSpeedRules.add(new ExtraFeeWindSpeedRule(10.0, 20.0, 0.5));
            windSpeedRules.add(new ExtraFeeWindSpeedRule(0.0, 9.9, 0.0));
            windSpeedRepository.saveAll(windSpeedRules);
            logger.info("Wind speed fee rules are added into the repository.");
        }

        if (weatherPhenomenonCount == 0) {
            logger.info("No weather phenomenon extra fee rules in the repository. Initializing....");
            List<ExtraFeeWeatherPhenomenonRule> weatherPhenomenonRules = new ArrayList<>();
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Clear", 0.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Few clouds", 0.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Variable clouds", 0.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Cloudy with clear spells", 0.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Overcast", 0.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Light snow shower", 1.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Moderate snow shower", 1.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Heavy snow shower", 1.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Light shower", 0.5));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Moderate shower", 0.5));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Heavy shower", 0.5));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Light rain", 0.5));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Moderate rain", 0.5));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Heavy rain", 0.5));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Glaze", -1.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Light sleet", 1.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Moderate sleet", 1.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Light snowfall", 1.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Moderate snowfall", 1.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Heavy snowfall", 1.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Blowing snow", 1.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Drifting snow", 1.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Hail", -1.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Mist", 0.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Fog", 0.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Thunder", -1.0));
            weatherPhenomenonRules.add(new ExtraFeeWeatherPhenomenonRule("Thunderstorm", -1.0));
            weatherPhenomenonRepository.saveAll(weatherPhenomenonRules);
            logger.info("Weather phenomenon fee rules are added into the repository.");
        }

        if (deliveryFeeCount == 0) {
            logger.info("No delivery fee entries in the repository. Initializing....");
            List<DeliveryFee> deliveryFeeList = new ArrayList<>();
            deliveryFeeList.add(new DeliveryFee("tallinn", "car", 4.0, Instant.now().truncatedTo(ChronoUnit.SECONDS)));
            deliveryFeeList.add(new DeliveryFee("tartu", "scooter", 3.0, Instant.now().truncatedTo(ChronoUnit.SECONDS)));
            deliveryFeeList.add(new DeliveryFee("pärnu", "bike", 2.0, Instant.now().truncatedTo(ChronoUnit.SECONDS)));
            deliveryFeeRepository.saveAll(deliveryFeeList);
            logger.info("Delivery fee entries are added into the repository.");
        }

        if (weatherDataCount == 0) {
            logger.info("No weather data entries in the repository. Initializing....");
            List<WeatherData> weatherDataList = new ArrayList<>();
            weatherDataList.add(new WeatherData("tallinn", 26038L, 10.0, 5.0, "Clear", Instant.now().truncatedTo(ChronoUnit.SECONDS)));
            weatherDataList.add(new WeatherData("tartu", 26242L, 1.0, 3.3, "Light rain", Instant.now().truncatedTo(ChronoUnit.SECONDS)));
            weatherDataList.add(new WeatherData("pärnu", 41803L, 22.3, 0.5, "Clear", Instant.now().truncatedTo(ChronoUnit.SECONDS)));
            weatherDataRepository.saveAll(weatherDataList);
            logger.info("Weather data entries are added into the repository.");
        }

    }
}

