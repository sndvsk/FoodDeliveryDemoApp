package com.example.FoodDeliveryDemoApp.component.calculations.feeRule.repository;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeAirTemperatureRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtraFeeAirTemperatureRuleRepository extends JpaRepository<ExtraFeeAirTemperatureRule, Long> {

    @Query("SELECT COUNT(w) FROM ExtraFeeAirTemperatureRule w " +
            "WHERE w.startAirTemperatureRange <= :endTemperatureRange " +
            "AND w.endAirTemperatureRange >= :startTemperatureRange")
    Long countOverlappingRanges(Double startTemperatureRange, Double endTemperatureRange);

    @Query("SELECT COUNT(w) FROM ExtraFeeAirTemperatureRule w " +
            "WHERE (" +
            "(w.startAirTemperatureRange <= :startTemperatureRange " +
            "AND w.endAirTemperatureRange >= :endTemperatureRange) " +
            "OR " +
            "(w.startAirTemperatureRange >= :startTemperatureRange " +
            "AND w.endAirTemperatureRange <= :endTemperatureRange))")
    Long countInsideRange(Double startTemperatureRange, Double endTemperatureRange);

    Optional<ExtraFeeAirTemperatureRule>
        findByStartAirTemperatureRangeLessThanEqualAndEndAirTemperatureRangeGreaterThanEqual
            (Double temperature, Double temperature2);

    Optional<ExtraFeeAirTemperatureRule>
        findByStartAirTemperatureRangeAndEndAirTemperatureRange(Double start, Double end);

    Optional<ExtraFeeAirTemperatureRule>
        findByStartAirTemperatureRangeAndEndAirTemperatureRangeAndFee(Double start, Double end, Double fee);

}
