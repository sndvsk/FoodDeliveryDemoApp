package com.example.FoodDeliveryDemoApp.integration.controller;

import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.domain.DeliveryFee;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
public class DeliveryFeeControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Value("${host.url.test}")
    private String hostUrl;

    private final String apiUrl = "/api/v1/delivery-fee";

    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setUp() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(1)
    public void testCalculateDeliveryFee() throws InterruptedException {
        // Arrange
        String city = "tallinn";
        String vehicleType = "car";

        // Act
        ResponseEntity<DeliveryFee> response = restTemplate.exchange(
                hostUrl + port + apiUrl + "?" +
                        String.format("city=%s&vehicleType=%s", city, vehicleType),
                HttpMethod.POST, new HttpEntity<>(null, headers), DeliveryFee.class
        );

        // Assert
        DeliveryFee deliveryFee = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertNotNull(deliveryFee);
        assertNotNull(deliveryFee.getId());
        assertNotNull(deliveryFee.getCity());
        assertNotNull(deliveryFee.getVehicleType());
        //noinspection ObviousNullCheck
        assertNotNull(deliveryFee.getDeliveryFee());
        assertNotNull(deliveryFee.getRest_timestamp());

        assertEquals(city, deliveryFee.getCity());
        assertEquals(vehicleType, deliveryFee.getVehicleType());

        TimeUnit.SECONDS.sleep(2);
        testGetExistingDeliveryFeeById(
                deliveryFee.getId(),
                deliveryFee.getCity(),
                deliveryFee.getVehicleType(),
                deliveryFee.getDeliveryFee(),
                deliveryFee.getRest_timestamp()
        );
    }

    @Test
    @Order(2)
    public void testGetAllExistingDeliveryFees() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // Make a request to get all delivery fees
        ResponseEntity<List<DeliveryFee>> response = restTemplate.exchange(
                hostUrl + port + apiUrl,
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );
        List<DeliveryFee> deliveryFeeList = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        // Assert that the API returns a non-empty list of delivery fees
        assertNotNull(deliveryFeeList);
        assertTrue(deliveryFeeList.size() > 0);
    }

    public void testGetExistingDeliveryFeeById(
            Long id, String city, String vehicleType, double deliveryFeePrice, OffsetDateTime timestamp) {

        ResponseEntity<DeliveryFee> response = restTemplate.exchange(
                hostUrl + port + apiUrl + "/" + id,
                HttpMethod.GET, new HttpEntity<>(null, headers), DeliveryFee.class
        );

        DeliveryFee deliveryFee = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(deliveryFee);
        assertNotNull(deliveryFee.getId());
        assertNotNull(deliveryFee.getCity());
        assertNotNull(deliveryFee.getVehicleType());
        //noinspection ObviousNullCheck
        assertNotNull(deliveryFee.getDeliveryFee());
        assertNotNull(deliveryFee.getRest_timestamp());

        assertEquals(id, deliveryFee.getId());
        assertEquals(city, deliveryFee.getCity());
        assertEquals(vehicleType, deliveryFee.getVehicleType());
        assertEquals(deliveryFeePrice, deliveryFee.getDeliveryFee());
        assertEquals(timestamp, deliveryFee.getRest_timestamp());

    }

    @Test
    @Order(3)
    public void testGetExistingDeliveryFeeByIdFail() {
        Long id = Long.MAX_VALUE;

        ResponseEntity<DeliveryFee> response = restTemplate.exchange(
                hostUrl + port + apiUrl + "/" + id,
                HttpMethod.GET, new HttpEntity<>(null, headers), DeliveryFee.class
        );

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    @Order(4)
    public void testGetExistingDeliveryFeeByIdFail2() {
        Long id = null;

        ResponseEntity<DeliveryFee> response = restTemplate.exchange(
                hostUrl + port + apiUrl + "/" + id,
                HttpMethod.GET, new HttpEntity<>(null, headers), DeliveryFee.class
        );

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

    }

}