package com.example.FoodDeliveryDemoApp.service.FeeRule;

import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeAirTemperatureRule;
import com.example.FoodDeliveryDemoApp.repository.rules.ExtraFeeAirTemperatureRuleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class ExtraFeeAirTemperatureRuleServiceImpl implements FeeRuleService {

    private ExtraFeeAirTemperatureRuleRepository airTemperatureRuleRepository;

    protected ExtraFeeAirTemperatureRuleServiceImpl(ExtraFeeAirTemperatureRuleRepository airTemperatureRuleRepository) {
        this.airTemperatureRuleRepository = airTemperatureRuleRepository;
    }

    public List<ExtraFeeAirTemperatureRule> getAllExtraFeeAirTemperatureRules() {
        return null;
    }

    public ExtraFeeAirTemperatureRule addExtraFeeAirTemperatureRule() {
        return null;
    }

    public ExtraFeeAirTemperatureRule getExtraFeeAirTemperatureRuleById(Long id) {
        return null;
    }

    public ExtraFeeAirTemperatureRule patchExtraFeeAirTemperatureRuleById(Long id) {
        return null;
    }

    public String deleteExtraFeeAirTemperatureRuleById(Long id) {
        return null;
    }

}
