package com.example.FoodDeliveryDemoApp.integration.controller.feeRuleController;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeWeatherPhenomenonRule;
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
public class ExtraFeeWeatherPhenomenonRuleFromControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Value("${host.url.test}")
    private String hostUrl;

    private final String apiUrl = "/api/v1/rules/fee/extra/phenomenon";

    @Test
    @Order(1)
    public void testGetAllExtraFeeWeatherPhenomenonRules() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        List<ExtraFeeWeatherPhenomenonRule> ruleList = new ObjectMapper().readValue(content, new TypeReference<>() {});

        assertTrue(ruleList.size() > 0);
        for (ExtraFeeWeatherPhenomenonRule rule : ruleList) {
            assertNotNull(rule.getId());
            assertNotNull(rule.getWeatherPhenomenonName());
            assertNotNull(rule.getFee());
        }
    }

    @Test
    @Order(2)
    public void testAddExtraFeeWeatherPhenomenonRule() throws Exception {
        String weatherPhenomenonName = "Its raining men hallelujah";
        Double fee = 99.0;

        // Send a POST request to the needed endpoint
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                hostUrl + port + apiUrl + "?weatherPhenomenonName=" + weatherPhenomenonName + "&fee=" + fee)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ExtraFeeWeatherPhenomenonRule responseRule = new ObjectMapper()
                .readValue(content,ExtraFeeWeatherPhenomenonRule.class);

        assertNotNull(responseRule);
        assertNotNull(responseRule.getId());
        assertNotNull(responseRule.getWeatherPhenomenonName());
        assertNotNull(responseRule.getFee());

        assertEquals(weatherPhenomenonName, responseRule.getWeatherPhenomenonName());
        assertEquals(fee, responseRule.getFee());

        TimeUnit.SECONDS.sleep(2);
        testGetExtraFeeWeatherPhenomenonRuleById(responseRule.getId());
        TimeUnit.SECONDS.sleep(2);
        testPatchExtraFeeWeatherPhenomenonRuleById(responseRule.getId());
        TimeUnit.SECONDS.sleep(2);
        testDeleteExtraFeeWeatherPhenomenonRuleById(responseRule.getId());
    }


    public void testGetExtraFeeWeatherPhenomenonRuleById(Long id) throws Exception {
        String weatherPhenomenonName = "Its raining men hallelujah";
        Double fee = 99.0;

        // Send a GET request to the needed endpoint
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ExtraFeeWeatherPhenomenonRule responseRule = new ObjectMapper()
                .readValue(content, ExtraFeeWeatherPhenomenonRule.class);

        assertNotNull(responseRule);
        assertNotNull(responseRule.getId());
        assertNotNull(responseRule.getWeatherPhenomenonName());
        assertNotNull(responseRule.getFee());

        assertEquals(id, responseRule.getId());
        assertEquals(weatherPhenomenonName, responseRule.getWeatherPhenomenonName());
        assertEquals(fee, responseRule.getFee());
    }

    public void testPatchExtraFeeWeatherPhenomenonRuleById(Long id) throws Exception {
        String weatherPhenomenonName = "Its raining men hallelujah";
        Double fee = 95.0;

        // Send a PATCH request to the needed endpoint
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(
                hostUrl + port + apiUrl + "/" + id + "?fee=" + fee)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ExtraFeeWeatherPhenomenonRule ruleResponse = new ObjectMapper()
                .readValue(content, ExtraFeeWeatherPhenomenonRule.class);

        assertNotNull(ruleResponse);
        assertNotNull(ruleResponse.getId());
        assertNotNull(ruleResponse.getWeatherPhenomenonName());
        assertNotNull(ruleResponse.getFee());

        assertEquals(id, ruleResponse.getId());
        assertEquals(weatherPhenomenonName, ruleResponse.getWeatherPhenomenonName());
        assertEquals(fee, ruleResponse.getFee());
    }

    public void testDeleteExtraFeeWeatherPhenomenonRuleById(Long id) throws Exception {
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
