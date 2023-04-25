package com.example.FoodDeliveryDemoApp.service.logger;

import com.example.FoodDeliveryDemoApp.domain.DeliveryFee;
import com.example.FoodDeliveryDemoApp.domain.WeatherData;
import com.example.FoodDeliveryDemoApp.domain.rules.RegionalBaseFeeRule;
import com.example.FoodDeliveryDemoApp.domain.rules.extraFee.ExtraFeeAirTemperatureRule;
import com.example.FoodDeliveryDemoApp.domain.rules.extraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.domain.rules.extraFee.ExtraFeeWindSpeedRule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoggingServiceImpl implements LoggingService {

    // based on https://github.com/oldfr/Basic-CRUD-API/tree/master/src/main/java/com/example/basiccrudAPIs

    final Logger logger = LoggerFactory.getLogger(LoggingServiceImpl.class);

    // can be implemented using lookup table (hashmap)

    /**
     * Converts an object to a string representation based on its class.
     * This method is used to generate a log message for the response body.
     *
     * @param obj the object to be converted to a string
    */
     private String getObjectString(Object obj) {
        switch (obj.getClass().getSimpleName()) {
            case "DeliveryFee":
                DeliveryFee deliveryFee = (DeliveryFee) obj;
                return "DeliveryFee = [" +
                        deliveryFee.getId() + ", " +
                        deliveryFee.getCity() + ", " +
                        deliveryFee.getVehicleType() + ", " +
                        deliveryFee.getDeliveryFee() + ", " +
                        deliveryFee.getWeatherId() + ", " +
                        deliveryFee.getTimestamp() +
                        "]";
            case "ExtraFeeWindSpeedRule":
                ExtraFeeWindSpeedRule rule = (ExtraFeeWindSpeedRule) obj;
                return "ExtraFeeWindSpeedRule = [" +
                        rule.getId() + ", " +
                        rule.getStartWindSpeedRange() + ", " +
                        rule.getEndWindSpeedRange() + ", " +
                        rule.getFee() +
                        "]";
            case "RegionalBaseFeeRule":
                RegionalBaseFeeRule regionalBaseFeeRule = (RegionalBaseFeeRule) obj;
                return "RegionalBaseFeeRule = [" +
                        regionalBaseFeeRule.getId() + ", " +
                        regionalBaseFeeRule.getCity() + ", " +
                        regionalBaseFeeRule.getWmoCode() + ", " +
                        regionalBaseFeeRule.getVehicleType() + ", " +
                        regionalBaseFeeRule.getFee() +
                        "]";
            case "WeatherData":
                WeatherData weatherData = (WeatherData) obj;
                return "WeatherData = [" +
                        weatherData.getId() + ", " +
                        weatherData.getStationName() + ", " +
                        weatherData.getWmoCode() + ", " +
                        weatherData.getAirTemperature() + ", " +
                        weatherData.getWindSpeed() + ", " +
                        weatherData.getWeatherPhenomenon() + ", " +
                        weatherData.getTimestamp() +
                        "]";
            case "ExtraFeeAirTemperatureRule":
                ExtraFeeAirTemperatureRule extraFeeAirTemperatureRule = (ExtraFeeAirTemperatureRule) obj;
                return "ExtraFeeAirTemperatureRule = [" +
                        extraFeeAirTemperatureRule.getId() + ", " +
                        extraFeeAirTemperatureRule.getStartAirTemperatureRange() + ", " +
                        extraFeeAirTemperatureRule.getEndAirTemperatureRange() + ", " +
                        extraFeeAirTemperatureRule.getFee() +
                        "]";
            case "ExtraFeeWeatherPhenomenonRule":
                ExtraFeeWeatherPhenomenonRule extraFeeWeatherPhenomenonRule = (ExtraFeeWeatherPhenomenonRule) obj;
                return " ExtraFeeWeatherPhenomenonRule = [" +
                        extraFeeWeatherPhenomenonRule.getId() + ", " +
                        extraFeeWeatherPhenomenonRule.getWeatherPhenomenonName() + ", " +
                        extraFeeWeatherPhenomenonRule.getFee() + ", " +
                        "]";
            case "String":
                String str = (String) obj;
                if (str.length() > 1000) {
                    logger.info("Big string response. Return only part of it.");
                    return " String = [" +
                            str.substring(0, 100) +
                            "]";
                }
                return " String = [" +
                        str +
                        "]";
            default:
                return " Unknown response body type";
        }
    }


    /**
     * A helper method to extract the headers from an HTTP response and return them as a Map.
     *
     * @param response the HttpServletResponse object representing the HTTP response
     * @return a Map containing the headers and their corresponding values from the HTTP response
     */
    private Map<String,String> getHeaders(HttpServletResponse response) {
        Map<String,String> headers = new HashMap<>();
        Collection<String> headerMap = response.getHeaderNames();
        for(String str : headerMap) {
            headers.put(str,response.getHeader(str));
        }
        return headers;
    }

    /**
     * A helper method to extract the query parameters from an HTTP request and return them as a Map.
     *
     * @param request the HttpServletRequest object representing the HTTP request
     * @return a Map containing the query parameters and their corresponding values from the HTTP request
     */
    private Map<String,String> getParameters(HttpServletRequest request) {
        Map<String,String> parameters = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName,paramValue);
        }
        return parameters;
    }

    /**
     * Displays the HTTP request information with the provided HttpServletRequest object and request body.
     * The request method, path and parameters are included in the message along with the request body if it's not null.
     *
     * @param request the HttpServletRequest object that contains the request information
     * @param body the request body object, can be null
     */
    @Override
    public void displayReq(HttpServletRequest request, Object body) {
        StringBuilder reqMessage = new StringBuilder();
        Map<String,String> parameters = getParameters(request);

        reqMessage.append("REQUEST ");
        reqMessage.append("method = [").append(request.getMethod()).append("]");
        reqMessage.append(" path = [").append(request.getRequestURI()).append("] ");

        if(!parameters.isEmpty()) {
            reqMessage.append(" parameters = [").append(parameters).append("] ");
        }

        if(!Objects.isNull(body)) {
            reqMessage.append(" body = [").append(body).append("]");
        }

        logger.info("log Request: {}", reqMessage);
    }

    /**
     * Logs the details of a HTTP response received by the server.
     *
     * @param request the HTTP request associated with the response
     * @param response the HTTP response to be logged
     * @param body the body of the response, if any
     */
    @Override
    public void displayResp(HttpServletRequest request, HttpServletResponse response, Object body) {
        StringBuilder respMessage = new StringBuilder();
        Map<String, String> headers = getHeaders(response);
        respMessage.append("RESPONSE ");
        respMessage.append("method = [").append(request.getMethod()).append("]");
        if (!headers.isEmpty()) {
            respMessage.append(" ResponseHeaders = [").append(headers).append("]");
        }
        if (body instanceof List<?> list) {
            for (Object obj : list) {
                String objString = getObjectString(obj);
                respMessage.append(" ").append(objString);
            }
        } else {
            String objString = getObjectString(body);
            respMessage.append(" ").append(objString);
        }
        logger.info("logResponse: {}", respMessage);
    }

}
