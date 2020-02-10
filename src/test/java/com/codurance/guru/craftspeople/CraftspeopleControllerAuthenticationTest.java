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
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
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
        String name = "mrocket@example.com";

        requestBody.put("mentorId", "1");
        requestBody.put("menteeId", "2");

        RestAssured.given()
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1ODEzNDU1NjMsImV4cCI6NDA3NDMzMTE2MywiYXVkIjoid3d3LnJvY2tldGVyLmNvbSIsInN1YiI6Impyb2NrZXRAcm9ja2V0ZXIuY29tIiwibmFtZSI6Ik1pY2hhZWwgUm9ja2V0IiwiU3VybmFtZSI6IlJvY2tldCIsIkVtYWlsIjoibXJvY2tldEBleGFtcGxlLmNvbSIsIlJvbGUiOlsiTWFuYWdlciIsIlByb2plY3QgQWRtaW5pc3RyYXRvciJdfQ.aAcIf677nk4zfv3X_gYhZFEyvhnB_FQQcXzNYuR0lYE")
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .post("craftspeople/mentor/add")
                .then()
                .statusCode(204);

        assertTrue(eventRepository.findAll().stream().filter(e -> e.getCreatedBy().equals(name)).count() == 1);
    }
}
