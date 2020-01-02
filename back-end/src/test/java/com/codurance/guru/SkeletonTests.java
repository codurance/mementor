package com.codurance.guru;

import com.codurance.guru.infrastructure.restclient.ClientTest;
import com.jayway.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GuruApplication.class)
@TestPropertySource(value={"classpath:application.properties"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SkeletonTests {

    @Autowired
    private ClientTest clientTest;

    @BeforeEach
    public void setBaseUri () {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void getDataTest() {
        get("/api/tdd/responseData")
                .then()
                .assertThat()
                .body("data", equalTo("responseData"));
    }

    @Test
    void client_api_test_call() {
        String expectedJSON = "{\"ok\":true}";
        String response = clientTest.testSlackApi();

        assertEquals(expectedJSON, response);
    }

}
