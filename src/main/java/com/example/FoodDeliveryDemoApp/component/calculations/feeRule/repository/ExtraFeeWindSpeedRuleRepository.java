package com.example.FoodDeliveryDemoApp.component.calculations.feeRule.repository;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeWindSpeedRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtraFeeWindSpeedRuleRepository extends JpaRepository<ExtraFeeWindSpeedRule, Long> {

    @Query("SELECT COUNT(w) FROM ExtraFeeWindSpeedRule w " +
            "WHERE w.startWindSpeedRange <= :endWindSpeedRange " +
            "AND w.endWindSpeedRange >= :startWindSpeedRange")
    Long countOverlappingRanges(Double startWindSpeedRange, Double endWindSpeedRange);

    @Query("SELECT COUNT(w) FROM ExtraFeeWindSpeedRule w " +
            "WHERE (" +
            "(w.startWindSpeedRange <= :startWindSpeedRange " +
            "AND w.endWindSpeedRange >= :endWindSpeedRange) " +
            "OR " +
            "(w.startWindSpeedRange >= :startWindSpeedRange " +
            "AND w.endWindSpeedRange <= :endWindSpeedRange))")
    Long countInsideRange(Double startWindSpeedRange, Double endWindSpeedRange);

    Optional<ExtraFeeWindSpeedRule>
        findByStartWindSpeedRangeLessThanEqualAndEndWindSpeedRangeGreaterThanEqual(Double windSpeed, Double windSpeed2);

    Optional<ExtraFeeWindSpeedRule> findByStartWindSpeedRangeAndEndWindSpeedRange(Double start, Double end);

    Optional<ExtraFeeWindSpeedRule>
        findByStartWindSpeedRangeAndEndWindSpeedRangeAndFee(Double start, Double end, Double fee);

}
