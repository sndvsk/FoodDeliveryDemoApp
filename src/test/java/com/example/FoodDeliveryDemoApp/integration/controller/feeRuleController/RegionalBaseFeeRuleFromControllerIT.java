package com.example.FoodDeliveryDemoApp.integration.controller.feeRuleController;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.RegionalBaseFeeRule;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
public class RegionalBaseFeeRuleFromControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Value("${host.url.test}")
    private String hostUrl;

    private final String apiUrl = "/api/v1/rules/fee/base";

    @Test
    @Order(1)
    public void testGetAllRegionalBaseFeeRules() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        List<RegionalBaseFeeRule> ruleList = new ObjectMapper().readValue(content, new TypeReference<>() {});

        assertNotNull(ruleList);
        assertTrue(ruleList.size() > 0);

        for (RegionalBaseFeeRule rule : ruleList) {
            assertNotNull(rule.getId());
            assertNotNull(rule.getCity());
            assertNotNull(rule.getWmoCode());
            assertNotNull(rule.getVehicleType());
            assertNotNull(rule.getFee());
        }

    }

    @Test
    @Order(2)
    public void testAddRegionalBaseFeeRule() throws Exception {
        // Create a new regional base fee rule
        String city = "tallinn";
        String vehicleType = "somestringthatisnotrealvehicletype";
        Long wmoCode = 26038L;
        Double fee = 5.0;

        // Send a POST request to the needed endpoint
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(hostUrl + port + apiUrl + "?" +
                                String.format("city=%s&vehicleType=%s&fee=%s", city, vehicleType, fee))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        RegionalBaseFeeRule responseRule = new ObjectMapper()
                .readValue(content,RegionalBaseFeeRule.class);

        assertNotNull(responseRule);
        assertNotNull(responseRule.getId());
        assertNotNull(responseRule.getCity());
        assertNotNull(responseRule.getWmoCode());
        assertNotNull(responseRule.getVehicleType());
        assertNotNull(responseRule.getFee());

        assertEquals(city, responseRule.getCity());
        assertEquals(wmoCode, responseRule.getWmoCode());
        assertEquals(vehicleType, responseRule.getVehicleType());
        assertEquals(fee, responseRule.getFee());

        TimeUnit.SECONDS.sleep(2);
        testGetRegionalBaseFeeRuleById(responseRule.getId());
        TimeUnit.SECONDS.sleep(2);
        testPatchGetRegionalBaseFeeRuleById(responseRule.getId());
        TimeUnit.SECONDS.sleep(2);
        testDeleteRegionalBaseFeeRuleById(responseRule.getId());
    }


    public void testGetRegionalBaseFeeRuleById(Long id) throws Exception {
        String city = "tallinn";
        String vehicleType = "somestringthatisnotrealvehicletype";
        Long wmoCode = 26038L;
        Double fee = 5.0;

        // Send a GET request to the needed endpoint
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        RegionalBaseFeeRule responseRule = new ObjectMapper()
                .readValue(content, RegionalBaseFeeRule.class);

        assertNotNull(responseRule);
        assertNotNull(responseRule.getId());
        assertNotNull(responseRule.getCity());
        assertNotNull(responseRule.getWmoCode());
        assertNotNull(responseRule.getVehicleType());
        assertNotNull(responseRule.getFee());

        assertEquals(id, responseRule.getId());
        assertEquals(city, responseRule.getCity());
        assertEquals(wmoCode, responseRule.getWmoCode());
        assertEquals(vehicleType, responseRule.getVehicleType());
        assertEquals(fee, responseRule.getFee());
    }

    public void testPatchGetRegionalBaseFeeRuleById(Long id) throws Exception {
        String city = "tallinn";
        String vehicleType = "somestringthatisnotrealvehicletype";
        Long wmoCode = 26038L;
        Double fee = 4.0;

        // Send a PATCH request to the needed endpoint
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(
                                hostUrl + port + apiUrl + "/" + id + "?fee=" + fee)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        RegionalBaseFeeRule ruleResponse = new ObjectMapper()
                .readValue(content, RegionalBaseFeeRule.class);

        assertNotNull(ruleResponse);
        assertNotNull(ruleResponse.getId());
        assertNotNull(ruleResponse.getCity());
        assertNotNull(ruleResponse.getWmoCode());
        assertNotNull(ruleResponse.getVehicleType());
        assertNotNull(ruleResponse.getFee());

        assertEquals(id, ruleResponse.getId());
        assertEquals(city, ruleResponse.getCity());
        assertEquals(wmoCode, ruleResponse.getWmoCode());
        assertEquals(vehicleType, ruleResponse.getVehicleType());
        assertEquals(fee, ruleResponse.getFee());
    }

    public void testDeleteRegionalBaseFeeRuleById(Long id) throws Exception {
        // Send a DELETE request to the needed endpoint
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(
                                hostUrl + port + apiUrl + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

}
