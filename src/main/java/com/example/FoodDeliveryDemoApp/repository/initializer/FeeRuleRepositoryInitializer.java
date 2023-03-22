package com.example.FoodDeliveryDemoApp.repository.initializer;

import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeAirTemperatureRule;
import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeWindSpeedRule;
import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;
import com.example.FoodDeliveryDemoApp.repository.rules.BaseFeeRuleRepository;
import com.example.FoodDeliveryDemoApp.repository.rules.ExtraFeeAirTemperatureRuleRepository;
import com.example.FoodDeliveryDemoApp.repository.rules.ExtraFeeWeatherPhenomenonRuleRepository;
import com.example.FoodDeliveryDemoApp.repository.rules.ExtraFeeWindSpeedRuleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeeRuleRepositoryInitializer implements CommandLineRunner {

    private final BaseFeeRuleRepository baseFeeRuleRepository;
    private final ExtraFeeAirTemperatureRuleRepository airTemperatureRepository;
    private final ExtraFeeWindSpeedRuleRepository windSpeedRepository;
    private final ExtraFeeWeatherPhenomenonRuleRepository weatherPhenomenonRepository;

    public FeeRuleRepositoryInitializer(BaseFeeRuleRepository baseFeeRuleRepository, ExtraFeeAirTemperatureRuleRepository airTemperatureRepository, ExtraFeeWindSpeedRuleRepository windSpeedRepository, ExtraFeeWeatherPhenomenonRuleRepository weatherPhenomenonRepository) {
        this.baseFeeRuleRepository = baseFeeRuleRepository;
        this.airTemperatureRepository = airTemperatureRepository;
        this.windSpeedRepository = windSpeedRepository;
        this.weatherPhenomenonRepository = weatherPhenomenonRepository;
    }

    @Override
    public void run(String... args) {

        long baseFeeCount = baseFeeRuleRepository.count();
        long airTemperatureCount = airTemperatureRepository.count();
        long windSpeedCount = windSpeedRepository.count();
        long weatherPhenomenonCount = weatherPhenomenonRepository.count();

        if (baseFeeCount == 0) {
            List<RegionalBaseFeeRule> baseFeeRules = new ArrayList<>();
            baseFeeRules.add(new RegionalBaseFeeRule("tallinn", "car", 4.0));
            baseFeeRules.add(new RegionalBaseFeeRule("tallinn", "scooter", 3.5));
            baseFeeRules.add(new RegionalBaseFeeRule("tallinn", "bike", 3.0));
            baseFeeRules.add(new RegionalBaseFeeRule("tartu", "car", 3.5));
            baseFeeRules.add(new RegionalBaseFeeRule("tartu", "scooter", 3.0));
            baseFeeRules.add(new RegionalBaseFeeRule("tartu", "bike", 2.5));
            baseFeeRules.add(new RegionalBaseFeeRule("pärnu", "car", 3.0));
            baseFeeRules.add(new RegionalBaseFeeRule("pärnu", "scooter", 2.5));
            baseFeeRules.add(new RegionalBaseFeeRule("pärnu", "bike", 2.0));
            baseFeeRuleRepository.saveAll(baseFeeRules);
        }

        if (airTemperatureCount == 0) {
            List<ExtraFeeAirTemperatureRule> airTemperatureRules = new ArrayList<>();
            airTemperatureRules.add(new ExtraFeeAirTemperatureRule(-10.1, -273.15, 1.0));
            airTemperatureRules.add(new ExtraFeeAirTemperatureRule(0.0, -10.0, 0.5));
            airTemperatureRules.add(new ExtraFeeAirTemperatureRule(0.1, 60.0, 0.0));
            airTemperatureRepository.saveAll(airTemperatureRules);
        }

        if (windSpeedCount == 0) {
            List<ExtraFeeWindSpeedRule> windSpeedRules = new ArrayList<>();
            windSpeedRules.add(new ExtraFeeWindSpeedRule(20.1, 120.0, -1.0));
            windSpeedRules.add(new ExtraFeeWindSpeedRule(10.0, 20.0, 0.5));
            windSpeedRules.add(new ExtraFeeWindSpeedRule(0.0, 9.9, 0.0));
            windSpeedRepository.saveAll(windSpeedRules);
        }

        if (weatherPhenomenonCount == 0) {
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

        }

    }

}
