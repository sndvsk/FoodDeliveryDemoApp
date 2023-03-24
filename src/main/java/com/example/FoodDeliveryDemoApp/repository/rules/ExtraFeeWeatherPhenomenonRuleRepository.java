package com.example.FoodDeliveryDemoApp.repository.rules;

import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWeatherPhenomenonRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtraFeeWeatherPhenomenonRuleRepository extends JpaRepository<ExtraFeeWeatherPhenomenonRule, Long> {

    Optional<ExtraFeeWeatherPhenomenonRule> findByWeatherPhenomenonName(String weatherPhenomenonName);

    Optional<ExtraFeeWeatherPhenomenonRule> findByWeatherPhenomenonNameAndFee(String weatherPhenomenonName, Double fee);

}
