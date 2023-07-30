package com.example.FoodDeliveryDemoApp.integration.controller;

import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.dto.DeliveryFeeDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"ADMIN"})
@AutoConfigureMockMvc
public class DeliveryFeeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Value("${host.url.test}")
    private String hostUrl;

    private final String apiUrl = "/api/v1/delivery-fee";

    @Test
    @Order(1)
    public void testCalculateDeliveryFee() throws Exception {
        // Arrange
        String city = "tallinn";
        String vehicleType = "car";

        // Send a POST request to the needed endpoint
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post( hostUrl + port + apiUrl + "?" +
                                String.format("city=%s&vehicleType=%s", city, vehicleType))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        DeliveryFeeDTO deliveryFee = objectMapper.readValue(content, DeliveryFeeDTO.class);

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
    public void testGetAllExistingDeliveryFees() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        List<DeliveryFeeDTO> deliveryFeeList = objectMapper.readValue(content, new TypeReference<>() {});

        // Assert that the API returns a non-empty list of delivery fees
        assertNotNull(deliveryFeeList);
        assertTrue(deliveryFeeList.size() > 0);
    }

    public void testGetExistingDeliveryFeeById(
            Long id, String city, String vehicleType,
            double deliveryFeePrice, OffsetDateTime timestamp) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        DeliveryFeeDTO deliveryFee = objectMapper.readValue(content, DeliveryFeeDTO.class);

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
    public void testGetExistingDeliveryFeeByIdFail() throws Exception {
        long id = Long.MAX_VALUE;

        mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    public void testGetExistingDeliveryFeeByIdFail2() throws Exception {
        Long id = null;

        mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}