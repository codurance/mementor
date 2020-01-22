package com.codurance.guru.configuration;

import com.codurance.guru.GuruApplication;
import com.codurance.guru.config.DynamicConfiguration;
import com.codurance.guru.config.DynamicConfigurationRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GuruApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DynamicConfigurationTest {


    @Autowired
    private DynamicConfigurationRepository repository;

    @Test
    public void get_config() {
        repository.deleteAll();
        repository.save(new DynamicConfiguration(1));

        RestAssured.get("/config")
                .then().assertThat()
                .statusCode(200)
                .body("lastMeetingThresholdsInWeeks", equalTo(1));
    }

    @Test
    public void get_config_will_always_return_the_latest_config() {
        repository.save(new DynamicConfiguration(1));
        repository.save(new DynamicConfiguration(3));
        repository.save(new DynamicConfiguration(2));
        repository.save(new DynamicConfiguration(4));

        RestAssured.get("/config")
                .then().assertThat()
                .statusCode(200)
                .body("lastMeetingThresholdsInWeeks", equalTo(4));
    }


    @Test
    public void config_can_be_updated() throws JSONException {
        repository.save(new DynamicConfiguration(4));

        JSONObject body = new JSONObject();
        body.put("lastMeetingThresholdsInWeeks", 2);

        RestAssured.
                given()
                .contentType("application/json")
                .body(body.toString())
                .put("/config")
                .then().assertThat()
                .statusCode(204);
    }
}
