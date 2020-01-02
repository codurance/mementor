package com.codurance.guru;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = GuruApplication.class)
@TestPropertySource(value={"classpath:application-test.properties"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceAPIShould {

    @LocalServerPort
    int port;


    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    @Sql({"/setup.sql"})
    @Sql(
            scripts = "/clean-up.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            config = @SqlConfig(transactionMode = ISOLATED)
    )
    public void retrieve_a_craftsperson() throws Exception{
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(String.format(
                "http://localhost:%s/craftspeople/1", port), HttpMethod.GET,entity, String.class);

        JSONObject expected = new JSONObject(){{
            put("id", 1);
            put("firstName", "Jose");
            put("lastName","Campos");
        }};

        JSONAssert.assertEquals(expected.toString(), response.getBody(), false);
    }

}
