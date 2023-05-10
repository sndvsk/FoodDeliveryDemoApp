package com.example.FoodDeliveryDemoApp.component.calculations.feeRule.repository;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeWeatherPhenomenonRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtraFeeWeatherPhenomenonRuleRepository extends JpaRepository<ExtraFeeWeatherPhenomenonRule, Long> {

    Optional<ExtraFeeWeatherPhenomenonRule> findByWeatherPhenomenonName(String weatherPhenomenonName);

    Optional<ExtraFeeWeatherPhenomenonRule> findByWeatherPhenomenonNameAndFee(String weatherPhenomenonName, Double fee);

}
