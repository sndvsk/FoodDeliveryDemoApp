package com.example.FoodDeliveryDemoApp.component.weatherItems.externalWeatherData.service;

import com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.dto.WeatherDataDTO;
import com.example.FoodDeliveryDemoApp.exception.ExternalServiceException;
import com.example.FoodDeliveryDemoApp.exception.UnauthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.StringReader;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ExternalWeatherDataServiceImpl implements ExternalWeatherDataService {

    @Value("${weather.data.url}")
    private String weatherObservationsUrl;

    private final WebClient webClient;

    public ExternalWeatherDataServiceImpl() {
        this.webClient = WebClient.builder()
                .defaultHeader("Accept", MediaType.APPLICATION_XML_VALUE)
                .build();
    }

    private WeatherDataDTO.Observations getObservations() throws JAXBException {
        String response = retrieveWeatherObservations();
        JAXBContext jaxbContext = JAXBContext.newInstance(WeatherDataDTO.Observations.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(response);
        @SuppressWarnings("UnnecessaryLocalVariable")
        WeatherDataDTO.Observations observations = (WeatherDataDTO.Observations) unmarshaller.unmarshal(reader);

        return observations;
    }

    /**
     * Retrieves weather observations from an external service using WebClient.
     *
     * @return a string representation of the weather observations response.
     * @throws NotFoundException if the requested data is not found.
     * @throws UnauthorizedException if the request is unauthorized.
     * @throws ExternalServiceException if there is an error in the external service.
     * @throws RuntimeException if an unknown error occurs.
     */
    @Override
    public String retrieveWeatherObservations() {
        try {
            String userAgent = "PostmanRuntime/7.31.1";

            return webClient.get()
                    .uri(weatherObservationsUrl)
                    .header("User-Agent", userAgent)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (WebClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new NotFoundException("Data not found");
            } else if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new UnauthorizedException("Unauthorized access");
            } else if (e.getStatusCode().is5xxServerError()) {
                throw new ExternalServiceException("Error in external service");
            } else {
                throw new RuntimeException("Unknown error occurred");
            }
        }
    }

    public Map<String, Long> getPossibleStationNamesAndCodes() throws JAXBException {
        WeatherDataDTO.Observations observations = getObservations();

        // TreeMap is better because you do not need to sort it and there can't be any duplicates
        Map<String, Long> stationNamesAndCodes = new TreeMap<>();
        for (WeatherDataDTO.Station station : observations.getStations()) {
            stationNamesAndCodes.put(station.getName(), station.getWmocode());
        }

        return stationNamesAndCodes;
    }

    public Map<String, Long> getPossibleStationNamesAndCodesFixedNaming() throws JAXBException {
        WeatherDataDTO.Observations observations = getObservations();

        // TreeMap is better because you do not need to sort it and there can't be any duplicates
        Map<String, Long> stationNamesAndCodes = new TreeMap<>();
        for (WeatherDataDTO.Station station : observations.getStations()) {
            // check if the station name exists in the fixedNaming map
            if (fixedNaming().containsKey(station.getName())) {
                // if it exists, use the corresponding fixed name as the key
                stationNamesAndCodes.put(fixedNaming().get(station.getName()), station.getWmocode());
            } else {
                // otherwise, use the original name as the key
                stationNamesAndCodes.put(station.getName(), station.getWmocode());
            }
        }

        return stationNamesAndCodes;
    }

    // todo make this mapping automated
    /**
     * This method returns a TreeMap that contains a fixed naming mapping.
     *
     * Each key represents the original name, while the value represents
     * the new fixed name.
     * @return a Map<String, String> object containing the fixed naming mapping.
     */
    public final Map<String, String> fixedNaming() {
        Map<String, String> nameMapping = new TreeMap<>();
        nameMapping.put("Aesoo", "aesoo");
        nameMapping.put("Ahja", "ahja");
        nameMapping.put("Alajõe", "alajõe");
        nameMapping.put("Arbavere", "arbavere");
        nameMapping.put("Audru", "audru");
        nameMapping.put("Dirhami", "dirhami");
        nameMapping.put("Elu", "elu");
        nameMapping.put("Elva", "elva");
        nameMapping.put("Haapsalu", "haapsalu");
        nameMapping.put("Haapsalu sadam", "haapsalu-sadam");
        nameMapping.put("Heltermaa", "heltermaa");
        nameMapping.put("Häädemeeste", "häädemeeste");
        nameMapping.put("Hüüru", "hüüru");
        nameMapping.put("Jõgeva", "jõgeva");
        nameMapping.put("Jõhvi", "jõhvi");
        nameMapping.put("Kaansoo", "kaansoo");
        nameMapping.put("Kasari", "kasari");
        nameMapping.put("Kehra", "kehra");
        nameMapping.put("Keila", "keila");
        nameMapping.put("Kihnu", "kihnu");
        nameMapping.put("Kirumpää", "kirumpää");
        nameMapping.put("Kloostrimetsa", "kloostrimetsa");
        nameMapping.put("Konuvere", "konuvere");
        nameMapping.put("Korela", "korela");
        nameMapping.put("Kulgu sadam", "kulgu-sadam");
        nameMapping.put("Kunda", "kunda");
        nameMapping.put("Kuningaküla", "kuningaküla");
        nameMapping.put("Kuusiku", "kuusiku");
        nameMapping.put("Kääpa", "kääpa");
        nameMapping.put("Laadi", "laadi");
        nameMapping.put("Loksa", "loksa");
        nameMapping.put("Luguse", "luguse");
        nameMapping.put("Lääne-Nigula", "lääne-nigula");
        nameMapping.put("Lüganuse", "lüganuse");
        nameMapping.put("Mehikoorma", "mehikoorma");
        nameMapping.put("Mustvee", "mustvee");
        nameMapping.put("Naissaare", "naissaare");
        nameMapping.put("Narva", "narva");
        nameMapping.put("Narva Karjääri", "narva-karjääri");
        nameMapping.put("Narva linn", "narva-linn");
        nameMapping.put("Narva-Jõesuu", "narva-jõesuu");
        nameMapping.put("Nurme", "nurme");
        nameMapping.put("Oore", "oore");
        nameMapping.put("Osmussaare", "osmussaare");
        nameMapping.put("Pajupea", "pajupea");
        nameMapping.put("Pajusi", "pajusi");
        nameMapping.put("Pakri", "pakri");
        nameMapping.put("Paldiski (Põhjasadam)", "paldiski-põhjasadam");
        nameMapping.put("Piigaste SMJ", "piigaste-smj");
        nameMapping.put("Pirita", "pirita");
        nameMapping.put("Praaga", "praaga");
        nameMapping.put("Pudisoo", "pudisoo");
        nameMapping.put("Pärnu", "pärnu");
        nameMapping.put("Pärnu-Sauga", "pärnu-sauga");
        nameMapping.put("Rannu-Jõesuu", "rannu-jõesuu");
        nameMapping.put("Reola", "reola");
        nameMapping.put("Riisa", "riisa");
        nameMapping.put("Ristna", "ristna");
        nameMapping.put("Rohuküla", "rohuküla");
        nameMapping.put("Rohuneeme", "rohuneeme");
        nameMapping.put("Roomassaare", "roomassaare");
        nameMapping.put("Roosisaare", "roosisaare");
        nameMapping.put("Roostoja", "roostoja");
        nameMapping.put("Ruhnu", "ruhnu");
        nameMapping.put("Räpina", "räpina");
        nameMapping.put("Separa", "separa");
        nameMapping.put("Sämi", "sämi");
        nameMapping.put("Sõrve", "sõrve");
        nameMapping.put("Taheva", "taheva");
        nameMapping.put("Tahkuse", "tahkuse");
        nameMapping.put("Tallinn-Harku", "tallinn");
        nameMapping.put("Tartu-Tõravere", "tartu");
        nameMapping.put("Tartu-Kvissental", "tartu-kvissental");
        nameMapping.put("Tarvastu", "tarvastu");
        nameMapping.put("Tiirikoja", "tiirikoja");
        nameMapping.put("Toila-Oru", "toila-oru");
        nameMapping.put("Tooma", "tooma");
        nameMapping.put("Tori", "tori");
        nameMapping.put("Tudulinna", "tudulinna");
        nameMapping.put("Tänassilma", "tänassilma");
        nameMapping.put("Tõlliste", "tõlliste");
        nameMapping.put("Tõrva", "tõrva");
        nameMapping.put("Tõrve", "tõrve");
        nameMapping.put("Türi", "türi");
        nameMapping.put("Türi-Alliku", "türi-alliku");
        nameMapping.put("Uue-Lõve", "uue-lõve");
        nameMapping.put("Uue-Lõve II", "uue-lõve-ii");
        nameMapping.put("Valga", "valga");
        nameMapping.put("Valgu", "valgu");
        nameMapping.put("Vanaküla", "vanaküla");
        nameMapping.put("Varangu", "varangu");
        nameMapping.put("Vasknarva", "vasknarva");
        nameMapping.put("Vihterpalu", "vihterpalu");
        nameMapping.put("Viljandi", "viljandi");
        nameMapping.put("Vilsandi", "vilsandi");
        nameMapping.put("Virtsu", "virtsu");
        nameMapping.put("Vodja", "vodja");
        nameMapping.put("Väike-Maarja", "väike-maarja");
        nameMapping.put("Võru", "võru");

        return nameMapping;
    }

}
