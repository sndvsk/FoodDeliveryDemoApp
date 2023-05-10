package com.example.FoodDeliveryDemoApp.component.calculations.feeRule.service.regionalBaseFee;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.RegionalBaseFeeRule;
import jakarta.xml.bind.JAXBException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RegionalBaseFeeRuleService {

    List<RegionalBaseFeeRule> getAllRegionalBaseFeeRules();

    RegionalBaseFeeRule addBaseFeeRule(String city, Long wmoCode, String vehicleType, Double fee) throws JAXBException;

    RegionalBaseFeeRule getRegionalBaseFeeRuleById(Long id);

    RegionalBaseFeeRule patchRegionalBaseFeeRuleById(
            Long id, String city, Long wmoCode, String vehicleType, Double fee) throws JAXBException;

    String deleteRegionalBaseFeeRuleById(Long id);

    Map<String, List<String>> getAllUniqueCitiesWithVehicleTypes();

    Set<String> getAllUniqueCities();

    RegionalBaseFeeRule getByCityAndVehicleType(String city, String vehicleType);
}
