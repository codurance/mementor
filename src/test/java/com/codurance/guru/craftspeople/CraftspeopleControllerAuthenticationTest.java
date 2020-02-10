package com.codurance.guru.craftspeople;

import com.codurance.guru.GuruApplication;
import com.codurance.guru.audits.EventRepository;
import com.codurance.guru.audits.EventService;
import com.codurance.guru.audits.TestJWTGenerator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.Is;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.*;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest(
        classes = GuruApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"should.filter.requests=true"})
public class CraftspeopleControllerAuthenticationTest {

    private JSONObject requestBody;

    @LocalServerPort
    int randomServerPort;

    @Before
    public void setUp() {
        requestBody = new JSONObject();
        RestAssured.port = randomServerPort;
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

    @Autowired
    EventRepository eventRepository;

    @Test
    public void an_event_is_saved() throws JSONException {
        String username = "edward.rixon@codurance.com";
        var token = TestJWTGenerator.createToken(username);

        requestBody.put("mentorId", "1");
        requestBody.put("menteeId", "2");

        var response = RestAssured.given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .post("craftspeople/mentor/add")
                .then()
                .statusCode(200)
                .extract()
                .response();

        assertThat(eventRepository.findAll().stream().filter(e -> e.getCreatedBy().equals(username)).count() == 1);
    }
}
