package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeAirTemperatureRule;
import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeWindSpeedRule;
import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;
import com.example.FoodDeliveryDemoApp.service.FeeRule.ExtraFee.AirTemperatureRule.ExtraFeeAirTemperatureRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.FeeRule.ExtraFee.WeatherPhenomenonRule.ExtraFeeWeatherPhenomenonRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.FeeRule.ExtraFee.WindSpeedRule.ExtraFeeWindSpeedRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.FeeRule.RegionalBaseFee.RegionalBaseFeeRuleServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@Tag(name = "Rules API", description = "Endpoint for managing delivery fee calculation business rules (base and extra fees).")
public class FeeRuleController {


    private final RegionalBaseFeeRuleServiceImpl regionalBaseFeeRuleService;
    private final ExtraFeeAirTemperatureRuleServiceImpl airTemperatureRuleService;
    private final ExtraFeeWindSpeedRuleServiceImpl windSpeedRuleService;
    private final ExtraFeeWeatherPhenomenonRuleServiceImpl weatherPhenomenonRuleService;

    public FeeRuleController(RegionalBaseFeeRuleServiceImpl regionalBaseFeeRuleService,
                             ExtraFeeAirTemperatureRuleServiceImpl airTemperatureRuleService,
                             ExtraFeeWindSpeedRuleServiceImpl windSpeedRuleService,
                             ExtraFeeWeatherPhenomenonRuleServiceImpl weatherPhenomenonRuleService) {
        this.regionalBaseFeeRuleService = regionalBaseFeeRuleService;
        this.airTemperatureRuleService = airTemperatureRuleService;
        this.windSpeedRuleService = windSpeedRuleService;
        this.weatherPhenomenonRuleService = weatherPhenomenonRuleService;
    }

    /*
    Regional base fee rule methods
________________________________________________________________________________________________________________________
     */

    @GetMapping(path ="/base-fee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RegionalBaseFeeRule>> getAllRegionalBaseFeeRules() {

        List<RegionalBaseFeeRule> response = regionalBaseFeeRuleService.getAllRegionalBaseFeeRules();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path="/base-fee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionalBaseFeeRule> addRegionalBaseFeeRule() {

        RegionalBaseFeeRule response = regionalBaseFeeRuleService.addBaseFeeRule();

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping(path ="/base-fee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionalBaseFeeRule> getRegionalBaseFeeRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @RequestParam Long id) {

        RegionalBaseFeeRule response = regionalBaseFeeRuleService.getRegionalBaseFeeRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(path ="/base-fee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionalBaseFeeRule> patchRegionalBaseFeeRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @RequestParam Long id) {

        RegionalBaseFeeRule response = regionalBaseFeeRuleService.patchRegionalBaseFeeRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path ="/base-fee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteRegionalBaseFeeRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @RequestParam Long id) {

        String response = regionalBaseFeeRuleService.deleteRegionalBaseFeeRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    Air temperature extra fee rule methods
________________________________________________________________________________________________________________________
     */

    @GetMapping(path ="/fee-temp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExtraFeeAirTemperatureRule>> getAllExtraFeeAirTemperatureRules() {

        List<ExtraFeeAirTemperatureRule> response = airTemperatureRuleService.getAllExtraFeeAirTemperatureRules();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path="/fee-temp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeAirTemperatureRule> addExtraFeeAirTemperatureRule() {

        ExtraFeeAirTemperatureRule response = airTemperatureRuleService.addExtraFeeAirTemperatureRule();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path ="/fee-temp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeAirTemperatureRule> getExtraFeeAirTemperatureRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @RequestParam Long id) {

        ExtraFeeAirTemperatureRule response = airTemperatureRuleService.getExtraFeeAirTemperatureRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(path="/fee-temp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeAirTemperatureRule> patchExtraFeeAirTemperatureRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @RequestParam Long id) {

        ExtraFeeAirTemperatureRule response = airTemperatureRuleService.patchExtraFeeAirTemperatureRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path="/fee-temp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteExtraFeeAirTemperatureRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @RequestParam Long id) {

        String response = airTemperatureRuleService.deleteExtraFeeAirTemperatureRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /*
    Wind speed extra fee rule methods
________________________________________________________________________________________________________________________
     */

    @GetMapping(path ="/fee-wind", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExtraFeeWindSpeedRule>> getAllExtraFeeWindSpeedRules() {

        List<ExtraFeeWindSpeedRule> response = windSpeedRuleService.getAllExtraFeeWindSpeedRules();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path="/fee-wind",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeWindSpeedRule> addExtraFeeWindSpeedRule() {

        ExtraFeeWindSpeedRule response = windSpeedRuleService.addExtraFeeWindSpeedRule();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path ="/fee-wind/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeWindSpeedRule> getExtraFeeWindSpeedRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @RequestParam Long id) {

        ExtraFeeWindSpeedRule response = windSpeedRuleService.getExtraFeeWindSpeedRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(path ="/fee-wind/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeWindSpeedRule> patchExtraFeeWindSpeedRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @RequestParam Long id) {

        ExtraFeeWindSpeedRule response = windSpeedRuleService.patchExtraFeeWindSpeedRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path ="/fee-wind/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteExtraFeeWindSpeedRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @RequestParam Long id) {

        String response = windSpeedRuleService.deleteExtraFeeWindSpeedRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    Weather phenomenon extra fee rule methods
________________________________________________________________________________________________________________________
     */

    @GetMapping(path ="/fee-phenomenon", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExtraFeeWeatherPhenomenonRule>> getAllExtraFeeWeatherPhenomenonRules() {
        List<ExtraFeeWeatherPhenomenonRule> response = weatherPhenomenonRuleService.getAllExtraFeeWeatherPhenomenonRules();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path="/fee-phenomenon",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeWeatherPhenomenonRule> addExtraFeeWeatherPhenomenonRule() {

        ExtraFeeWeatherPhenomenonRule response = weatherPhenomenonRuleService.addExtraFeeWeatherPhenomenonRule();

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping(path ="/fee-phenomenon/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeWeatherPhenomenonRule> getExtraFeeWeatherPhenomenonRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @RequestParam Long id) {

        ExtraFeeWeatherPhenomenonRule response = weatherPhenomenonRuleService.getExtraFeeWeatherPhenomenonRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(path ="/fee-phenomenon/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeWeatherPhenomenonRule> patchExtraFeeWeatherPhenomenonRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @RequestParam Long id) {

        ExtraFeeWeatherPhenomenonRule response = weatherPhenomenonRuleService.patchExtraFeeWeatherPhenomenonRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path ="/fee-phenomenon/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteExtraFeeWeatherPhenomenonRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @RequestParam Long id) {

        String response = weatherPhenomenonRuleService.deleteExtraFeeWeatherPhenomenonRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
