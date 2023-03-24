package com.example.FoodDeliveryDemoApp.service.feeRule.regionalBaseFee;

import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;
import jakarta.xml.bind.JAXBException;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public interface RegionalBaseFeeRuleService {

    List<RegionalBaseFeeRule> getAllRegionalBaseFeeRules();

    RegionalBaseFeeRule addBaseFeeRule(String city, Long wmoCode, String vehicleType, Double fee) throws JAXBException;

    RegionalBaseFeeRule getRegionalBaseFeeRuleById(Long id);

    RegionalBaseFeeRule patchRegionalBaseFeeRuleById(Long id, String city, Long wmoCode, String vehicleType, Double fee) throws JAXBException;

    String deleteRegionalBaseFeeRuleById(Long id);

    TreeMap<String, List<String>> getAllUniqueCitiesWithVehicleTypes();

    Set<String> getAllUniqueCities();

    RegionalBaseFeeRule getByCityAndVehicleType(String city, String vehicleType);
}
