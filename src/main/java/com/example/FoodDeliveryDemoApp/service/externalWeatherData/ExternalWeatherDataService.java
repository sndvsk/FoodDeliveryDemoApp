package com.example.FoodDeliveryDemoApp.service.externalWeatherData;

import jakarta.xml.bind.JAXBException;

import java.util.Map;
import java.util.TreeMap;

public interface ExternalWeatherDataService {

    String retrieveWeatherObservations();

    Map<String, Long> getPossibleStationNamesAndCodes() throws JAXBException;

    Map<String, Long> getPossibleStationNamesAndCodesFixedNaming() throws JAXBException;

    Map<String, String> fixedNaming();

}