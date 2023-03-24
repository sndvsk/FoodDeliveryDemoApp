package com.example.FoodDeliveryDemoApp.service.externalWeatherData;

import jakarta.xml.bind.JAXBException;

import java.util.TreeMap;

public interface ExternalWeatherDataService {

    String retrieveWeatherObservations();

    TreeMap<String, Long> getPossibleStationNamesAndCodes() throws JAXBException;

    TreeMap<String, Long> getPossibleStationNamesAndCodesFixedNaming() throws JAXBException;

    TreeMap<String, String> fixedNaming();

}