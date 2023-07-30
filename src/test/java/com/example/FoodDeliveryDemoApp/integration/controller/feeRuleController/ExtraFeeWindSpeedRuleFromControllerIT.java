package com.example.FoodDeliveryDemoApp.integration.controller.feeRuleController;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeAirTemperatureRule;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeWindSpeedRule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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

import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
public class ExtraFeeWindSpeedRuleFromControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Value("${host.url.test}")
    private String hostUrl;

    private final String apiUrl = "/api/v1/rules/fee/extra/windspeed";

    @Test
    @Order(1)
    public void testGetAllExtraFeeWindSpeedRules() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        List<ExtraFeeWindSpeedRule> ruleList = new ObjectMapper().readValue(content, new TypeReference<>() {});

        assertNotNull(ruleList);
        assertTrue(ruleList.size() > 0);

        for (ExtraFeeWindSpeedRule rule : ruleList) {
            assertNotNull(rule.getId());
            assertNotNull(rule.getStartWindSpeedRange());
            assertNotNull(rule.getEndWindSpeedRange());
            assertNotNull(rule.getFee());
        }

    }

    @Test
    @Order(2)
    public void testAddExtraFeeWindSpeedRule() throws Exception {
        Double startRange = 30.0;
        Double endRange = 40.0;
        Double fee = 15.0;

        // Send a POST request to the needed endpoint
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post( hostUrl + port + apiUrl + "?" +
                                String.format("startWindSpeedRange=%s&endWindSpeedRange=%s&fee=%s",
                                        startRange, endRange, fee))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ExtraFeeWindSpeedRule responseRule = new ObjectMapper()
                .readValue(content,ExtraFeeWindSpeedRule.class);

        assertNotNull(responseRule);
        assertNotNull(responseRule.getId());
        assertNotNull(responseRule.getStartWindSpeedRange());
        assertNotNull(responseRule.getEndWindSpeedRange());
        assertNotNull(responseRule.getFee());

        assertEquals(startRange, responseRule.getStartWindSpeedRange());
        assertEquals(endRange, responseRule.getEndWindSpeedRange());
        assertEquals(fee, responseRule.getFee());

        TimeUnit.SECONDS.sleep(2);
        testGetExtraFeeWindSpeedRuleById(responseRule.getId());
        TimeUnit.SECONDS.sleep(2);
        testPatchExtraFeeWindSpeedRuleById(responseRule.getId());
        TimeUnit.SECONDS.sleep(2);
        testDeleteExtraFeeWindSpeedRuleById(responseRule.getId());
    }

    public void testGetExtraFeeWindSpeedRuleById(Long id) throws Exception {
        Double startRange = 30.0;
        Double endRange = 40.0;
        Double fee = 15.0;

        // Send a GET request to the needed endpoint
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ExtraFeeWindSpeedRule responseRule = new ObjectMapper()
                .readValue(content, ExtraFeeWindSpeedRule.class);

        assertNotNull(responseRule);
        assertNotNull(responseRule.getId());
        assertNotNull(responseRule.getStartWindSpeedRange());
        assertNotNull(responseRule.getEndWindSpeedRange());
        assertNotNull(responseRule.getFee());

        assertEquals(id, responseRule.getId());
        assertEquals(startRange, responseRule.getStartWindSpeedRange());
        assertEquals(endRange, responseRule.getEndWindSpeedRange());
        assertEquals(fee, responseRule.getFee());
    }

    public void testPatchExtraFeeWindSpeedRuleById(Long id) throws Exception {
        Double startRange = 30.0;
        Double endRange = 40.0;
        Double fee = 12.0;

        // Send a PATCH request to the needed endpoint
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(
                                hostUrl + port + apiUrl + "/" + id + "?fee=" + fee)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ExtraFeeWindSpeedRule ruleResponse = new ObjectMapper()
                .readValue(content, ExtraFeeWindSpeedRule.class);

        assertNotNull(ruleResponse);
        assertNotNull(ruleResponse.getId());
        assertNotNull(ruleResponse.getStartWindSpeedRange());
        assertNotNull(ruleResponse.getEndWindSpeedRange());
        assertNotNull(ruleResponse.getFee());

        assertEquals(id, ruleResponse.getId());
        assertEquals(startRange, ruleResponse.getStartWindSpeedRange());
        assertEquals(endRange, ruleResponse.getEndWindSpeedRange());
        assertEquals(fee, ruleResponse.getFee());
    }

    public void testDeleteExtraFeeWindSpeedRuleById(Long id) throws Exception {
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
