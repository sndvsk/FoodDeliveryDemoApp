package com.example.FoodDeliveryDemoApp.service.FeeRule.ExtraFee.WindSpeedRule;

import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeWindSpeedRule;
import com.example.FoodDeliveryDemoApp.repository.rules.ExtraFeeWindSpeedRuleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExtraFeeWindSpeedRuleServiceImpl implements ExtraFeeWindSpeedRuleService {

    private ExtraFeeWindSpeedRuleRepository windSpeedRuleRepository;

    public ExtraFeeWindSpeedRuleServiceImpl(ExtraFeeWindSpeedRuleRepository windSpeedRuleRepository) {
        this.windSpeedRuleRepository = windSpeedRuleRepository;
    }

    public List<ExtraFeeWindSpeedRule> getAllExtraFeeWindSpeedRules() {
        return null;
    }

    public ExtraFeeWindSpeedRule addExtraFeeWindSpeedRule() {
        return null;
    }

    public ExtraFeeWindSpeedRule getExtraFeeWindSpeedRuleById(Long id) {
        return null;
    }

    public ExtraFeeWindSpeedRule patchExtraFeeWindSpeedRuleById(Long id) {
        return null;
    }

    public String deleteExtraFeeWindSpeedRuleById(Long id) {
        return null;
    }

}
