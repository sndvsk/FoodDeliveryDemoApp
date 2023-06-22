package com.example.FoodDeliveryDemoApp.integration.controller.feeRuleController;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeAirTemperatureRule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.*;
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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.TestPropertySource;
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
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
public class ExtraFeeAirTemperatureRuleFromControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Value("${host.url.test}")
    private String hostUrl;

    private final String apiUrl = "/api/v1/rules/fee/extra/temperature";

    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setUp() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(1)
    public void getAllExtraFeeAirTemperatureRules() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<List<ExtraFeeAirTemperatureRule>> response = restTemplate.exchange(
                hostUrl + port + apiUrl,
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );
        List<ExtraFeeAirTemperatureRule> ruleList = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        assertNotNull(ruleList);
        assertTrue(ruleList.size() > 0);

        for (ExtraFeeAirTemperatureRule rule : ruleList) {
            assertNotNull(rule.getId());
            assertNotNull(rule.getStartAirTemperatureRange());
            assertNotNull(rule.getEndAirTemperatureRange());
            assertNotNull(rule.getFee());
        }

    }

    @Test
    @Order(2)
    public void addExtraFeeAirTemperatureRule() throws InterruptedException, IOException {
        Double startRange = -25.0;
        Double endRange = -30.0;
        Double fee = 5.0;

        // Send a POST request to the needed endpoint
        ResponseEntity<ExtraFeeAirTemperatureRule> response = restTemplate.exchange(
                hostUrl + port + apiUrl + "?" +
                        String.format("startTemperatureRange=%s&endTemperatureRange=%s&fee=%s",
                                startRange, endRange, fee),

                HttpMethod.POST, new HttpEntity<>(null, headers), ExtraFeeAirTemperatureRule.class
        );

        // Assert that the API returns the created rule with a status of CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ExtraFeeAirTemperatureRule responseRule = response.getBody();

        assertNotNull(responseRule);
        assertNotNull(responseRule.getId());
        assertNotNull(responseRule.getStartAirTemperatureRange());
        assertNotNull(responseRule.getEndAirTemperatureRange());
        assertNotNull(responseRule.getFee());

        assertEquals(startRange, responseRule.getStartAirTemperatureRange());
        assertEquals(endRange, responseRule.getEndAirTemperatureRange());
        assertEquals(fee, responseRule.getFee());

        TimeUnit.SECONDS.sleep(2);
        testGetExtraFeeAirTemperatureRuleById(responseRule.getId());
        TimeUnit.SECONDS.sleep(2);
        testPatchExtraFeeAirTemperatureRuleById(responseRule.getId());
        TimeUnit.SECONDS.sleep(2);
        testDeleteExtraFeeAirTemperatureRuleById(responseRule.getId());
    }


    public void testGetExtraFeeAirTemperatureRuleById(Long id) {
        Double startRange = -25.0;
        Double endRange = -30.0;
        Double fee = 5.0;

        // Send a GET request to the needed endpoint
        ResponseEntity<ExtraFeeAirTemperatureRule> response = restTemplate.exchange(
                hostUrl + port + apiUrl + "/" + id,
                HttpMethod.GET, new HttpEntity<>(null, headers), ExtraFeeAirTemperatureRule.class);

        // Assert that the API returns the created rule with a status of OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ExtraFeeAirTemperatureRule responseRule = response.getBody();

        assertNotNull(responseRule);
        assertNotNull(responseRule.getId());
        assertNotNull(responseRule.getStartAirTemperatureRange());
        assertNotNull(responseRule.getEndAirTemperatureRange());
        assertNotNull(responseRule.getFee());

        assertEquals(id, responseRule.getId());
        assertEquals(startRange, responseRule.getStartAirTemperatureRange());
        assertEquals(endRange, responseRule.getEndAirTemperatureRange());
        assertEquals(fee, responseRule.getFee());
    }

    public void testPatchExtraFeeAirTemperatureRuleById(Long id) throws IOException {
        Double startRange = -25.0;
        Double endRange = -30.0;
        Double fee = 10.0;

        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        OkHttpClient client = new OkHttpClient();

        HttpUrl url = HttpUrl.parse(hostUrl + port + apiUrl + "/" + id);
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

        ExtraFeeAirTemperatureRule ruleResponse =
                objectMapper.readValue(Objects.requireNonNull(response.body()).bytes(),
                        ExtraFeeAirTemperatureRule.class);

        assertNotNull(ruleResponse);
        assertNotNull(ruleResponse.getId());
        assertNotNull(ruleResponse.getStartAirTemperatureRange());
        assertNotNull(ruleResponse.getEndAirTemperatureRange());
        assertNotNull(ruleResponse.getFee());

        assertEquals(id, ruleResponse.getId());
        assertEquals(startRange, ruleResponse.getStartAirTemperatureRange());
        assertEquals(endRange, ruleResponse.getEndAirTemperatureRange());
        assertEquals(fee, ruleResponse.getFee());
    }

    public void testDeleteExtraFeeAirTemperatureRuleById(Long id) {
        // Send a GET request to the needed endpoint
        ResponseEntity<String> response = restTemplate.exchange(
                hostUrl + port + apiUrl + "/" + id,
                HttpMethod.DELETE, new HttpEntity<>(null, headers), String.class);

        // Assert that the API returns the created rule with a status of OK
        String ruleResponse = response.getBody();
        assertNotNull(ruleResponse);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
