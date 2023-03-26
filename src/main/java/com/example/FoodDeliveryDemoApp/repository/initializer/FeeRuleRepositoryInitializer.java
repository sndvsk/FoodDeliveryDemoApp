package com.example.FoodDeliveryDemoApp.repository.initializer;

import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeAirTemperatureRule;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWindSpeedRule;
import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;
import com.example.FoodDeliveryDemoApp.repository.rules.RegionalBaseFeeRuleRepository;
import com.example.FoodDeliveryDemoApp.repository.rules.ExtraFeeAirTemperatureRuleRepository;
import com.example.FoodDeliveryDemoApp.repository.rules.ExtraFeeWeatherPhenomenonRuleRepository;
import com.example.FoodDeliveryDemoApp.repository.rules.ExtraFeeWindSpeedRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeeRuleRepositoryInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(FeeRuleRepositoryInitializer.class);

    private final RegionalBaseFeeRuleRepository baseFeeRuleRepository;
    private final ExtraFeeAirTemperatureRuleRepository airTemperatureRepository;
    private final ExtraFeeWindSpeedRuleRepository windSpeedRepository;
    private final ExtraFeeWeatherPhenomenonRuleRepository weatherPhenomenonRepository;

    public FeeRuleRepositoryInitializer(RegionalBaseFeeRuleRepository baseFeeRuleRepository, ExtraFeeAirTemperatureRuleRepository airTemperatureRepository, ExtraFeeWindSpeedRuleRepository windSpeedRepository, ExtraFeeWeatherPhenomenonRuleRepository weatherPhenomenonRepository) {
        this.baseFeeRuleRepository = baseFeeRuleRepository;
        this.airTemperatureRepository = airTemperatureRepository;
        this.windSpeedRepository = windSpeedRepository;
        this.weatherPhenomenonRepository = weatherPhenomenonRepository;
    }

    @Override
    public void run(String... args) {

        logger.info("Checking if there are regional base fee rules in the repository.");
        long baseFeeCount = baseFeeRuleRepository.count();

        logger.info("Checking if there are air temperature extra fee rules in the repository.");
        long airTemperatureCount = airTemperatureRepository.count();

        logger.info("Checking if there are wind speed extra fee rules in the repository.");
        long windSpeedCount = windSpeedRepository.count();

        logger.info("Checking if there are weather component extra fee rules in the repository.");
        long weatherPhenomenonCount = weatherPhenomenonRepository.count();

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
            airTemperatureRules.add(new ExtraFeeAirTemperatureRule(-10.1, -273.15, 1.0));
            airTemperatureRules.add(new ExtraFeeAirTemperatureRule(0.0, -10.0, 0.5));
            airTemperatureRules.add(new ExtraFeeAirTemperatureRule(0.1, 60.0, 0.0));
            airTemperatureRepository.saveAll(airTemperatureRules);
            logger.info("Air temperature fee rules are added into the repository.");
        }

        if (windSpeedCount == 0) {
            logger.info("No wind speed  extra fee rules in the repository. Initializing....");
            List<ExtraFeeWindSpeedRule> windSpeedRules = new ArrayList<>();
            windSpeedRules.add(new ExtraFeeWindSpeedRule(20.1, 120.0, -1.0));
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

    }

}
