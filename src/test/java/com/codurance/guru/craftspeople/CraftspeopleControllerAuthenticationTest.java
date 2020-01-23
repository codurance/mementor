package com.codurance.guru.craftspeople;

import com.codurance.guru.GuruApplication;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest(
        classes = GuruApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"should.filter.requests=true"})
public class CraftspeopleControllerAuthenticationTest {

    private JSONObject requestBody;

    @Before
    public void setUp() {
        requestBody = new JSONObject();
    }

    @Test
    public void get_unauthorized_response_when_missing_authorization_header() {
        given()
                .contentType("application/json")
                .get("craftspeople")
                .then()
                .statusCode(401);
    }

    @Test
    public void get_unauthorized_response_when_blank_authorization_header() {
        given()
                .contentType("application/json")
                .header("Authorization", "")
                .get("craftspeople")
                .then()
                .statusCode(401);
    }

    @Test
    public void get_unauthorized_response_when_invalid_authorization_token() {
        given()
                .contentType("application/json")
                .header("Authorization", "invalidHeader")
                .get("craftspeople")
                .then()
                .statusCode(401);
    }
}
