package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeAirTemperatureRule;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWindSpeedRule;
import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.airTemperatureRule.ExtraFeeAirTemperatureRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.weatherPhenomenonRule.ExtraFeeWeatherPhenomenonRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.windSpeedRule.ExtraFeeWindSpeedRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.regionalBaseFee.RegionalBaseFeeRuleServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@Tag(name = "Rules API", description = "Endpoint for managing delivery fee calculation business rules (base and extra fees)")
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

    // TODO add rest interface documentation

    /*
    Regional base fee rule methods
________________________________________________________________________________________________________________________
     */

    @GetMapping(path ="/fee/base", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RegionalBaseFeeRule>> getAllRegionalBaseFeeRules() {

        List<RegionalBaseFeeRule> response = regionalBaseFeeRuleService.getAllRegionalBaseFeeRules();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path="/fee/base", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionalBaseFeeRule> addRegionalBaseFeeRule(
            @RequestParam String city,
            @RequestParam String vehicleType,
            @RequestParam Double fee) {

        RegionalBaseFeeRule response = regionalBaseFeeRuleService.addBaseFeeRule(city, vehicleType, fee);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping(path ="/fee/base/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionalBaseFeeRule> getRegionalBaseFeeRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        RegionalBaseFeeRule response = regionalBaseFeeRuleService.getRegionalBaseFeeRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(path ="/fee/base/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionalBaseFeeRule> patchRegionalBaseFeeRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) Double fee) {

        RegionalBaseFeeRule response = regionalBaseFeeRuleService.patchRegionalBaseFeeRuleById(id, city, vehicleType, fee);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path ="/fee/base/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteRegionalBaseFeeRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        String response = regionalBaseFeeRuleService.deleteRegionalBaseFeeRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    Air temperature extra fee rule methods
________________________________________________________________________________________________________________________
     */

    @GetMapping(path ="/fee/extra/temperature", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExtraFeeAirTemperatureRule>> getAllExtraFeeAirTemperatureRules() {

        List<ExtraFeeAirTemperatureRule> response = airTemperatureRuleService.getAllExtraFeeAirTemperatureRules();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path="/fee/extra/temperature", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeAirTemperatureRule> addExtraFeeAirTemperatureRule(
            @RequestParam Double startTemperatureRange,
            @RequestParam Double endTemperatureRange,
            @RequestParam Double fee) {

        ExtraFeeAirTemperatureRule response = airTemperatureRuleService.addExtraFeeAirTemperatureRule(startTemperatureRange, endTemperatureRange, fee);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path ="/fee/extra/temperature/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeAirTemperatureRule> getExtraFeeAirTemperatureRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        ExtraFeeAirTemperatureRule response = airTemperatureRuleService.getExtraFeeAirTemperatureRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(path="/fee/extra/temperature/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeAirTemperatureRule> patchExtraFeeAirTemperatureRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id,
            @RequestParam(required = false) Double startTemperatureRange,
            @RequestParam(required = false) Double endTemperatureRange,
            @RequestParam(required = false) Double fee) {

        ExtraFeeAirTemperatureRule response = airTemperatureRuleService.patchExtraFeeAirTemperatureRuleById(id, startTemperatureRange, endTemperatureRange, fee);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path="/fee/extra/temperature/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteExtraFeeAirTemperatureRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        String response = airTemperatureRuleService.deleteExtraFeeAirTemperatureRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /*
    Wind speed extra fee rule methods
________________________________________________________________________________________________________________________
     */

    @GetMapping(path = "/fee/extra/windspeed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExtraFeeWindSpeedRule>> getAllExtraFeeWindSpeedRules() {

        List<ExtraFeeWindSpeedRule> response = windSpeedRuleService.getAllExtraFeeWindSpeedRules();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/fee/extra/windspeed",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeWindSpeedRule> addExtraFeeWindSpeedRule(
            @RequestParam Double startWindSpeedRange,
            @RequestParam Double endWindSpeedRange,
            @RequestParam Double fee) {

        ExtraFeeWindSpeedRule response = windSpeedRuleService.addExtraFeeWindSpeedRule(startWindSpeedRange, endWindSpeedRange, fee);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/fee/extra/windspeed/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeWindSpeedRule> getExtraFeeWindSpeedRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        ExtraFeeWindSpeedRule response = windSpeedRuleService.getExtraFeeWindSpeedRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(path = "/fee/extra/windspeed/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeWindSpeedRule> patchExtraFeeWindSpeedRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id,
            @RequestParam(required = false) Double startWindSpeedRange,
            @RequestParam(required = false) Double endWindSpeedRange,
            @RequestParam(required = false) Double fee) {

        ExtraFeeWindSpeedRule response = windSpeedRuleService.patchExtraFeeWindSpeedRuleById(id, startWindSpeedRange, endWindSpeedRange, fee);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/fee/extra/windspeed/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteExtraFeeWindSpeedRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        String response = windSpeedRuleService.deleteExtraFeeWindSpeedRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    Weather phenomenon extra fee rule methods
________________________________________________________________________________________________________________________
     */

    // I added all weather phenomenon types into database on first load.
    // see: com/example/FoodDeliveryDemoApp/repository/initializer/FeeRuleRepositoryInitializer.java

    @GetMapping(path = "/fee/extra/phenomenon", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExtraFeeWeatherPhenomenonRule>> getAllExtraFeeWeatherPhenomenonRules() {
        List<ExtraFeeWeatherPhenomenonRule> response = weatherPhenomenonRuleService.getAllExtraFeeWeatherPhenomenonRules();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/fee/extra/phenomenon",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeWeatherPhenomenonRule> addExtraFeeWeatherPhenomenonRule(
            @RequestParam String weatherPhenomenonName,
            @RequestParam Double fee) {

        ExtraFeeWeatherPhenomenonRule response = weatherPhenomenonRuleService.addExtraFeeWeatherPhenomenonRule(weatherPhenomenonName, fee);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping(path = "/fee/extra/phenomenon/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeWeatherPhenomenonRule> getExtraFeeWeatherPhenomenonRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        ExtraFeeWeatherPhenomenonRule response = weatherPhenomenonRuleService.getExtraFeeWeatherPhenomenonRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(path = "/fee/extra/phenomenon/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraFeeWeatherPhenomenonRule> patchExtraFeeWeatherPhenomenonRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id,
            @RequestParam(required = false) String weatherPhenomenonName,
            @RequestParam(required = false) Double fee) {

        ExtraFeeWeatherPhenomenonRule response = weatherPhenomenonRuleService.patchExtraFeeWeatherPhenomenonRuleById(id, weatherPhenomenonName, fee);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/fee/extra/phenomenon/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteExtraFeeWeatherPhenomenonRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        String response = weatherPhenomenonRuleService.deleteExtraFeeWeatherPhenomenonRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
