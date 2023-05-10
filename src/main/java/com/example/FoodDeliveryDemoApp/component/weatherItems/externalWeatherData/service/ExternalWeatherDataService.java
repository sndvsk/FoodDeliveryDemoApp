package com.example.FoodDeliveryDemoApp.component.weatherItems.externalWeatherData.service;

import jakarta.xml.bind.JAXBException;

import java.util.Map;

public interface ExternalWeatherDataService {

    String retrieveWeatherObservations();

    Map<String, Long> getPossibleStationNamesAndCodes() throws JAXBException;

    Map<String, Long> getPossibleStationNamesAndCodesFixedNaming() throws JAXBException;

    Map<String, String> fixedNaming();

}