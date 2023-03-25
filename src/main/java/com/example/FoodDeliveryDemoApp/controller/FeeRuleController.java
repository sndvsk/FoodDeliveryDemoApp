package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeAirTemperatureRule;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWindSpeedRule;
import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.airTemperatureRule.ExtraFeeAirTemperatureRuleService;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.airTemperatureRule.ExtraFeeAirTemperatureRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.weatherPhenomenonRule.ExtraFeeWeatherPhenomenonRuleService;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.weatherPhenomenonRule.ExtraFeeWeatherPhenomenonRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.windSpeedRule.ExtraFeeWindSpeedRuleService;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.windSpeedRule.ExtraFeeWindSpeedRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.regionalBaseFee.RegionalBaseFeeRuleService;
import com.example.FoodDeliveryDemoApp.service.feeRule.regionalBaseFee.RegionalBaseFeeRuleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.JAXBException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("DefaultAnnotationParam")
@RestController
@RequestMapping("/api/rules")
@Tag(name = "Rules API", description = "Endpoint for managing delivery fee calculation business rules (base and extra fees)")
public class FeeRuleController {

    private final RegionalBaseFeeRuleService regionalBaseFeeRuleService;
    private final ExtraFeeAirTemperatureRuleService airTemperatureRuleService;
    private final ExtraFeeWindSpeedRuleService windSpeedRuleService;
    private final ExtraFeeWeatherPhenomenonRuleService weatherPhenomenonRuleService;

    public FeeRuleController(RegionalBaseFeeRuleService regionalBaseFeeRuleService,
                             ExtraFeeAirTemperatureRuleService airTemperatureRuleService,
                             ExtraFeeWindSpeedRuleService windSpeedRuleService,
                             ExtraFeeWeatherPhenomenonRuleService weatherPhenomenonRuleService) {
        this.regionalBaseFeeRuleService = regionalBaseFeeRuleService;
        this.airTemperatureRuleService = airTemperatureRuleService;
        this.windSpeedRuleService = windSpeedRuleService;
        this.weatherPhenomenonRuleService = weatherPhenomenonRuleService;
    }

    /*
    Regional base fee rule methods
________________________________________________________________________________________________________________________
     */

    /**
     * Retrieve all regional base fee rules
     *
     * @return ResponseEntity containing a list of RegionalBaseFeeRule objects and HTTP status code of OK
     */
    @GetMapping(path ="/fee/base", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all regional base fee rules")
    public ResponseEntity<List<RegionalBaseFeeRule>> getAllRegionalBaseFeeRules() {

        List<RegionalBaseFeeRule> response = regionalBaseFeeRuleService.getAllRegionalBaseFeeRules();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Add regional base fee rule. You can do it with city name or wmo code.
     *
     * @param city (optional) the name of the city
     * @param wmoCode (optional) the WMO code of the city
     * @param vehicleType the vehicle type
     * @param fee the regional base fee for this combination of city and vehicle type
     * @return ResponseEntity containing a RegionalBaseFeeRule object and a HttpStatus of CREATED
     * @throws JAXBException if there is an error parsing the XML request body
     */
    @PostMapping(path="/fee/base", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add regional base fee rule. You can do it with city name or wmo code.")
    public ResponseEntity<RegionalBaseFeeRule> addRegionalBaseFeeRule(
            @Parameter(name = "city", description = "City name", example = "")
            @RequestParam(required = false) String city,
            @Parameter(name = "wmoCode", description = "Wmo code of the city", example = "")
            @RequestParam(required = false) Long wmoCode,
            @Parameter(name = "vehicleType", description = "Vehicle type", example = "")
            @RequestParam String vehicleType,
            @Parameter(name = "fee", description = "Regional base fee for this combination of city and vehicle type", example = "")
            @RequestParam Double fee) throws JAXBException {

        RegionalBaseFeeRule response = regionalBaseFeeRuleService.addBaseFeeRule(city, wmoCode, vehicleType, fee);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    /**
     * Get regional base fee rule by its id.
     *
     * @param id the id of the rule
     * @return ResponseEntity containing a RegionalBaseFeeRule object and a HttpStatus of OK
     */
    @GetMapping(path ="/fee/base/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get regional base fee rule by id")
    public ResponseEntity<RegionalBaseFeeRule> getRegionalBaseFeeRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        RegionalBaseFeeRule response = regionalBaseFeeRuleService.getRegionalBaseFeeRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Patch regional base fee rule by id. You can do it with city name or wmo code
     *
     * @param id the id of the rule
     * @param city (optional) the name of the city
     * @param wmoCode (optional) the WMO code of the city
     * @param vehicleType (optional) the vehicle type
     * @param fee (optional) the regional base fee for this combination of city and vehicle type
     * @return ResponseEntity containing a RegionalBaseFeeRule object and a HttpStatus of OK
     * @throws JAXBException if there is an error parsing the XML request body
     */
    @PatchMapping(path ="/fee/base/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Patch regional base fee rule by id. You can do it with city name or wmo code.")
    public ResponseEntity<RegionalBaseFeeRule> patchRegionalBaseFeeRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id,
            @Parameter(name = "city", description = "City name", example = "")
            @RequestParam(required = false) String city,
            @Parameter(name = "wmoCode", description = "Wmo code of the city", example = "")
            @RequestParam(required = false) Long wmoCode,
            @Parameter(name = "vehicleType", description = "Vehicle type", example = "")
            @RequestParam(required = false) String vehicleType,
            @Parameter(name = "fee", description = "Regional base fee for this combination of city and vehicle type", example = "")
            @RequestParam(required = false) Double fee) throws JAXBException {

        RegionalBaseFeeRule response = regionalBaseFeeRuleService.patchRegionalBaseFeeRuleById(id, city, wmoCode, vehicleType, fee);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete regional base fee rule by its id
     *
     * @param id the id of the rule
     * @return ResponseEntity containing a String object and a HttpStatus of OK
     */
    @DeleteMapping(path ="/fee/base/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete regional base fee rule by id")
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

    /**
     * Retrieve all air temperature extra fee rules.
     *
     * @return ResponseEntity containing a list of ExtraFeeAirTemperatureRule objects and HTTP status code of OK
     */
    @GetMapping(path ="/fee/extra/temperature", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all air temperature extra fee rules")
    public ResponseEntity<List<ExtraFeeAirTemperatureRule>> getAllExtraFeeAirTemperatureRules() {

        List<ExtraFeeAirTemperatureRule> response = airTemperatureRuleService.getAllExtraFeeAirTemperatureRules();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Add a new air temperature extra fee rule.
     *
     * @param startTemperatureRange start air temperature of the air temperature range
     * @param endTemperatureRange end air temperature of the air temperature range
     * @param fee air temperature extra fee for this air temperature range
     * @return ResponseEntity containing an ExtraFeeAirTemperatureRule object and HTTP status code of CREATED
     */
    @PostMapping(path="/fee/extra/temperature", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add air temperature extra fee rule. Example: start: -10.0 -> end: 0.0")
    public ResponseEntity<ExtraFeeAirTemperatureRule> addExtraFeeAirTemperatureRule(
            @Parameter(name = "startTemperatureRange", description = "Start air temperature of air temperature range", example = "")
            @RequestParam Double startTemperatureRange,
            @Parameter(name = "endTemperatureRange", description = "End air temperature of air temperature range", example = "")
            @RequestParam Double endTemperatureRange,
            @Parameter(name = "fee", description = "Air temperature extra fee for this air temperature range", example = "")
            @RequestParam Double fee) {

        ExtraFeeAirTemperatureRule response = airTemperatureRuleService.addExtraFeeAirTemperatureRule(startTemperatureRange, endTemperatureRange, fee);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieve an air temperature extra fee rule by its id.
     *
     * @param id The ID of the rule to retrieve
     * @return ResponseEntity containing an ExtraFeeAirTemperatureRule object and HTTP status code of OK
     */
    @GetMapping(path ="/fee/extra/temperature/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get air temperature extra fee rule by id")
    public ResponseEntity<ExtraFeeAirTemperatureRule> getExtraFeeAirTemperatureRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        ExtraFeeAirTemperatureRule response = airTemperatureRuleService.getExtraFeeAirTemperatureRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Update an air temperature extra fee rule by its id.
     *
     * @param id the id of the rule to update
     * @param startTemperatureRange (optional) start air temperature of the air temperature range
     * @param endTemperatureRange (optional) end air temperature of the air temperature range
     * @param fee (optional) air temperature extra fee for this air temperature range
     * @return ResponseEntity containing an ExtraFeeAirTemperatureRule object and HTTP status code of OK
     */
    @PatchMapping(path="/fee/extra/temperature/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Patch air temperature extra fee rule by id. Example: start: -10.0 -> end: 0.0")
    public ResponseEntity<ExtraFeeAirTemperatureRule> patchExtraFeeAirTemperatureRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id,
            @Parameter(name = "startTemperatureRange", description = "Start air temperature of air temperature range", example = "")
            @RequestParam(required = false) Double startTemperatureRange,
            @Parameter(name = "endTemperatureRange", description = "End air temperature of air temperature range", example = "")
            @RequestParam(required = false) Double endTemperatureRange,
            @Parameter(name = "fee", description = "Air temperature extra fee for this air temperature range", example = "")
            @RequestParam(required = false) Double fee) {

        ExtraFeeAirTemperatureRule response = airTemperatureRuleService.patchExtraFeeAirTemperatureRuleById(id, startTemperatureRange, endTemperatureRange, fee);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete an air temperature extra fee rule by its id.
     *
     * @param id the id of the rule to retrieve
     * @return ResponseEntity containing a String object and HTTP status code of OK
     */
    @DeleteMapping(path="/fee/extra/temperature/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete air temperature extra fee rule by id")
    public ResponseEntity<String> deleteExtraFeeAirTemperatureRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        String response = airTemperatureRuleService.deleteExtraFeeAirTemperatureRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    Wind speed extra fee rule methods
________________________________________________________________________________________________________________________
     */

    /**
     * Retrieve all wind temperature extra fee rules.
     *
     * @return ResponseEntity containing a list of ExtraFeeWindSpeedRule objects and HTTP status code of OK
     */
    @GetMapping(path = "/fee/extra/windspeed", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all wind speed extra fee rules")
    public ResponseEntity<List<ExtraFeeWindSpeedRule>> getAllExtraFeeWindSpeedRules() {

        List<ExtraFeeWindSpeedRule> response = windSpeedRuleService.getAllExtraFeeWindSpeedRules();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Add a new wind speed extra fee rule.
     *
     * @param startWindSpeedRange start wind speed of wind speed range
     * @param endWindSpeedRange end wind speed of wind speed range
     * @param fee wind speed extra fee for this wind speed range
     * @return ResponseEntity containing an ExtraFeeWindSpeedRule object and HTTP status code of CREATED
     */
    @PostMapping(path = "/fee/extra/windspeed",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add wind speed extra fee rule. Example: start: 0.0 -> end: 20.0")
    public ResponseEntity<ExtraFeeWindSpeedRule> addExtraFeeWindSpeedRule(
            @Parameter(name = "startWindSpeedRange", description = "Start wind speed of wind speed range", example = "")
            @RequestParam Double startWindSpeedRange,
            @Parameter(name = "endWindSpeedRange", description = "End wind speed of wind speed range", example = "")
            @RequestParam Double endWindSpeedRange,
            @Parameter(name = "fee", description = "Wind speed extra fee for this wind speed range", example = "")
            @RequestParam Double fee) {

        ExtraFeeWindSpeedRule response = windSpeedRuleService.addExtraFeeWindSpeedRule(startWindSpeedRange, endWindSpeedRange, fee);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieve a wind speed extra fee rule by its id.
     *
     * @param id the id of the rule to retrieve.
     * @return ResponseEntity containing an ExtraFeeWindSpeedRule object and HTTP status code of OK
     */
    @GetMapping(path = "/fee/extra/windspeed/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get wind speed extra fee rule by id")
    public ResponseEntity<ExtraFeeWindSpeedRule> getExtraFeeWindSpeedRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        ExtraFeeWindSpeedRule response = windSpeedRuleService.getExtraFeeWindSpeedRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Update a wind speed extra fee rule by its id.
     *
     * @param id the id of the rule to update.
     * @param startWindSpeedRange (optional) start wind speed of wind speed range
     * @param endWindSpeedRange (optional) end wind speed of wind speed range
     * @param fee (optional) wind speed extra fee for this wind speed range
     * @return ResponseEntity containing an ExtraFeeWindSpeedRule object and HTTP status code of OK.
     */
    @PatchMapping(path = "/fee/extra/windspeed/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Patch wind speed extra fee rule by id. Example: start: 0.0 -> end: 20.0")
    public ResponseEntity<ExtraFeeWindSpeedRule> patchExtraFeeWindSpeedRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id,
            @Parameter(name = "startWindSpeedRange", description = "Start wind speed of wind speed range", example = "")
            @RequestParam(required = false) Double startWindSpeedRange,
            @Parameter(name = "endWindSpeedRange", description = "End wind speed of wind speed range", example = "")
            @RequestParam(required = false) Double endWindSpeedRange,
            @Parameter(name = "fee", description = "Wind speed extra fee for this wind speed range", example = "")
            @RequestParam(required = false) Double fee) {

        ExtraFeeWindSpeedRule response = windSpeedRuleService.patchExtraFeeWindSpeedRuleById(id, startWindSpeedRange, endWindSpeedRange, fee);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete a wind speed extra fee rule by its id.
     *
     * @param id the id of the rule to retrieve
     * @return ResponseEntity containing a String object and HTTP status code of OK
     */
    @DeleteMapping(path = "/fee/extra/windspeed/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete wind speed extra fee rule by id")
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

    /**
     * Retrieve all weather phenomenon extra fee rules.
     *
     * @return ResponseEntity containing a list of ExtraFeeWeatherPhenomenonRule objects and HTTP status code of OK
     */
    @GetMapping(path = "/fee/extra/phenomenon", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all weather phenomenon extra fee rules")
    public ResponseEntity<List<ExtraFeeWeatherPhenomenonRule>> getAllExtraFeeWeatherPhenomenonRules() {

        List<ExtraFeeWeatherPhenomenonRule> response = weatherPhenomenonRuleService.getAllExtraFeeWeatherPhenomenonRules();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // no need for it because all existing weather phenomenon's for external weather api are already in database
    /**
     * Add a new weather phenomenon extra fee rule.
     *
     * @param weatherPhenomenonName name of weather phenomenon
     * @param fee weather phenomenon extra fee for this weather phenomenon
     * @return ResponseEntity containing an ExtraFeeWeatherPhenomenonRule object and HTTP status code of OK
     */
    @PostMapping(path = "/fee/extra/phenomenon",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add weather phenomenon extra fee rule. (All supported by external weather api weather phenomenon's are already in the database. Consider patching instead of adding new ones)")
    public ResponseEntity<ExtraFeeWeatherPhenomenonRule> addExtraFeeWeatherPhenomenonRule(
            @Parameter(name = "weatherPhenomenonName", description = "Name of weather phenomenon", example = "")
            @RequestParam String weatherPhenomenonName,
            @Parameter(name = "fee", description = "Weather phenomenon extra fee for this weather phenomenon", example = "")
            @RequestParam Double fee) {

        ExtraFeeWeatherPhenomenonRule response = weatherPhenomenonRuleService.addExtraFeeWeatherPhenomenonRule(weatherPhenomenonName, fee);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping(path = "/fee/extra/phenomenon/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get weather phenomenon extra fee rule by id")
    public ResponseEntity<ExtraFeeWeatherPhenomenonRule> getExtraFeeWeatherPhenomenonRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        ExtraFeeWeatherPhenomenonRule response = weatherPhenomenonRuleService.getExtraFeeWeatherPhenomenonRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Update a weather phenomenon extra fee rule by its id.
     *
     * @param id the id of the rule to update
     * @param weatherPhenomenonName (optional) name of weather phenomenon
     * @param fee (optional) weather phenomenon extra fee for this weather phenomenon
     * @return ResponseEntity containing an ExtraFeeWeatherPhenomenonRule object and HTTP status code of OK
     */
    @PatchMapping(path = "/fee/extra/phenomenon/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Patch weather phenomenon extra fee rule by id")
    public ResponseEntity<ExtraFeeWeatherPhenomenonRule> patchExtraFeeWeatherPhenomenonRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id,
            @Parameter(name = "weatherPhenomenonName", description = "Name of weather phenomenon", example = "")
            @RequestParam(required = false) String weatherPhenomenonName,
            @Parameter(name = "fee", description = "Weather phenomenon extra fee for this weather phenomenon", example = "")
            @RequestParam(required = false) Double fee) {

        ExtraFeeWeatherPhenomenonRule response = weatherPhenomenonRuleService.patchExtraFeeWeatherPhenomenonRuleById(id, weatherPhenomenonName, fee);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete a weather phenomenon extra fee rule by its id.
     *
     * @param id the id of the rule to retrieve
     * @return ResponseEntity containing a String object and HTTP status code of OK
     */
    @DeleteMapping(path = "/fee/extra/phenomenon/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete weather phenomenon extra fee rule by id")
    public ResponseEntity<String> deleteExtraFeeWeatherPhenomenonRuleById(
            @Parameter(name = "id", description = "Id of the rule")
            @PathVariable Long id) {

        String response = weatherPhenomenonRuleService.deleteExtraFeeWeatherPhenomenonRuleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
