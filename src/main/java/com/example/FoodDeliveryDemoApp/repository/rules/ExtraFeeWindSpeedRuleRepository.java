package com.example.FoodDeliveryDemoApp.repository.rules;

import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWindSpeedRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtraFeeWindSpeedRuleRepository extends JpaRepository<ExtraFeeWindSpeedRule, Long> {

    @Query("SELECT COUNT(w) FROM ExtraFeeWindSpeedRule w WHERE w.startWindSpeedRange <= :endWindSpeedRange AND w.endWindSpeedRange >= :startWindSpeedRange")
    Long countOverlappingRanges(Double startWindSpeedRange, Double endWindSpeedRange);

    Optional<ExtraFeeWindSpeedRule> findByStartWindSpeedRangeLessThanEqualAndEndWindSpeedRangeGreaterThanEqual(Double windSpeed, Double windSpeed2);

}
