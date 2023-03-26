package com.example.FoodDeliveryDemoApp.integration.controller.feeRuleController;

import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWindSpeedRule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integration.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Profile("test")
public class ExtraFeeWindSpeedRuleFromControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setUp() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Sql("/wind-rule-fix.sql")
    @Order(1)
    public void testGetAllExtraFeeWindSpeedRules() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<List<ExtraFeeWindSpeedRule>> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/rules/fee/extra/windspeed",
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );
        List<ExtraFeeWindSpeedRule> ruleList = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);

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
    @Sql("/wind-rule-fix.sql")
    @Order(2)
    public void testAddExtraFeeWindSpeedRule() throws InterruptedException, IOException {
        Double startRange = 30.0;
        Double endRange = 40.0;
        Double fee = 15.0;

        // Send a POST request to the needed endpoint
        ResponseEntity<ExtraFeeWindSpeedRule> response = restTemplate.exchange(
                "http://localhost:" + port +
                        String.format("/api/rules/fee/extra/" +
                                "windspeed?startWindSpeedRange=%s&endWindSpeedRange=%s&fee=%s",
                                startRange, endRange, fee),
                HttpMethod.POST, new HttpEntity<>(null, headers), ExtraFeeWindSpeedRule.class
        );

        // Assert that the API returns the created rule with a status of CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ExtraFeeWindSpeedRule responseRule = response.getBody();

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


    public void testGetExtraFeeWindSpeedRuleById(Long id) {
        Double startRange = 30.0;
        Double endRange = 40.0;
        Double fee = 15.0;

        // Send a GET request to the needed endpoint
        ResponseEntity<ExtraFeeWindSpeedRule> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/rules/fee/extra/windspeed/" + id,
                HttpMethod.GET, new HttpEntity<>(null, headers), ExtraFeeWindSpeedRule.class);

        // Assert that the API returns the created rule with a status of OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ExtraFeeWindSpeedRule responseRule = response.getBody();

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

    public void testPatchExtraFeeWindSpeedRuleById(Long id) throws IOException {
        Double startRange = 30.0;
        Double endRange = 40.0;
        Double fee = 12.0;

        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        OkHttpClient client = new OkHttpClient();

        HttpUrl url = HttpUrl.parse("http://localhost:" + port + "/api/rules/fee/extra/windspeed/" + id);
        assertNotNull(url);

        HttpUrl.Builder urlBuilder = url.newBuilder();
        urlBuilder.addQueryParameter("fee", String.valueOf(fee));
        url = urlBuilder.build();

        //noinspection deprecation
        Request request = new Request.Builder()
                .url(url)
                .patch(RequestBody.create(null, new byte[0]))
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(response.code(), HttpStatus.OK.value());

        ExtraFeeWindSpeedRule ruleResponse =
                objectMapper.readValue(Objects.requireNonNull(response.body()).bytes(), ExtraFeeWindSpeedRule.class);

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

    public void testDeleteExtraFeeWindSpeedRuleById(Long id) {
        // Send a GET request to the needed endpoint
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/rules/fee/extra/windspeed/" + id,
                HttpMethod.DELETE, new HttpEntity<>(null, headers), String.class);

        // Assert that the API returns the created rule with a status of OK
        String ruleResponse = response.getBody();
        assertNotNull(ruleResponse);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
