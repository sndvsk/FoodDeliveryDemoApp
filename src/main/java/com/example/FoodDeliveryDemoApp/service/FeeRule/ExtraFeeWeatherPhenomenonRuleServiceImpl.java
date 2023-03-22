package com.example.FoodDeliveryDemoApp.service.FeeRule;

import com.example.FoodDeliveryDemoApp.exception.feerule.FeeRuleNotFoundException;
import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.repository.rules.ExtraFeeWeatherPhenomenonRuleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public abstract class ExtraFeeWeatherPhenomenonRuleServiceImpl implements FeeRuleService {

    private final ExtraFeeWeatherPhenomenonRuleRepository weatherPhenomenonRepository;

    protected ExtraFeeWeatherPhenomenonRuleServiceImpl(ExtraFeeWeatherPhenomenonRuleRepository weatherPhenomenonRuleRepository) {
        this.weatherPhenomenonRepository = weatherPhenomenonRuleRepository;
    }

    public List<ExtraFeeWeatherPhenomenonRule> getAllExtraFeeWeatherPhenomenonRules() {
        return null;
    }

    public ExtraFeeWeatherPhenomenonRule addExtraFeeWeatherPhenomenonRule() {
        return null;
    }

    public ExtraFeeWeatherPhenomenonRule getExtraFeeWeatherPhenomenonRuleById(Long id) {
        return null;
    }

    public ExtraFeeWeatherPhenomenonRule patchExtraFeeWeatherPhenomenonRuleById(Long id) {
        return null;
    }

    public String deleteExtraFeeWeatherPhenomenonRuleById(Long id) {
        return null;
    }

    public ExtraFeeWeatherPhenomenonRule findByWeatherPhenomenonName(String weatherPhenomenonName) {
        Optional<ExtraFeeWeatherPhenomenonRule> weatherPhenomenon = weatherPhenomenonRepository.findByWeatherPhenomenonName(weatherPhenomenonName);
        return weatherPhenomenon.
                orElseThrow(() -> new FeeRuleNotFoundException(String.format("Extra fee rule for this: ´%s´ weather phenomenon does not exist", weatherPhenomenonName)));
    }

}
