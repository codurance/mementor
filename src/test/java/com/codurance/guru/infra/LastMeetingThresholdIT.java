package com.codurance.guru.infra;

import com.codurance.guru.GuruApplication;
import com.codurance.guru.core.configuration.lastmeeting.threshold.LastMeetingThreshold;
import com.codurance.guru.infra.persistence.repository.LastMeetingThresholdRepositoryImpl;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GuruApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LastMeetingThresholdIT {

    @Autowired
    private LastMeetingThresholdRepositoryImpl repository;
    private ValidatableResponse response;
    private JSONObject body;

    @LocalServerPort
    int serverPort;

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
    }

    @Test
    public void get_config() {
        given_an_empty_repository();
        given_a_configuration_with(1);

        when_the_config_is_retrieved();

        then_the_threshold_config_is(1);
    }

    @Test
    public void get_config_will_always_return_the_latest_config() {
        given_a_configuration_with(1);
        given_a_configuration_with(3);
        given_a_configuration_with(2);
        given_a_configuration_with(4);

        when_the_config_is_retrieved();

        then_the_threshold_config_is(4);
    }

    @Test
    public void config_can_be_updated() throws JSONException {
        given_a_configuration_with(4);
        given_a_json_config_to_insert(2);

        when_the_config_is_updated();

        response.statusCode(204);

        // valueOf to fix ambiguous method signatures
        Assert.assertEquals(Integer.valueOf(2), repository.getConfig().getLastMeetingThresholdsInWeeks());
    }

    @Test
    public void the_update_will_not_add_a_new_config_to_the_repo() throws JSONException {
        given_a_configuration_with(4);
        given_a_json_config_to_insert(2);
        long currentNumbersOfConfig = repository.count();

        when_the_config_is_updated();

        response.statusCode(204);
        Assert.assertEquals(currentNumbersOfConfig, repository.count());
    }

    @Test
    public void null_values_should_be_rejected() throws JSONException {
        given_a_json_config_to_insert(null);

        when_the_config_is_updated();
        then_there_is_an_error_with("Last meeting threshold cannot be null");
    }

    @Test
    public void zero_or_negative_values_should_be_rejected() throws JSONException {
        given_a_json_config_to_insert(-1);
        when_the_config_is_updated();
        then_there_is_an_error_with("Last meeting threshold must be greater than 0");

        given_a_json_config_to_insert(0);
        when_the_config_is_updated();
        then_there_is_an_error_with("Last meeting threshold must be greater than 0");
    }

    private void given_a_configuration_with(int threshold) {
        repository.save(new LastMeetingThreshold(threshold));
    }

    private void given_an_empty_repository() {
        repository.deleteAll();
    }

    private void given_a_json_config_to_insert(Integer threshold) throws JSONException {
        body = new JSONObject();
        body.put("lastMeetingThresholdsInWeeks", threshold);
    }

    private void when_the_config_is_retrieved() {
        response = RestAssured.get("/config")
                .then().assertThat()
                .statusCode(200);
    }

    private void when_the_config_is_updated() {
        response = RestAssured.
                given()
                .contentType("application/json")
                .body(body.toString())
                .put("/config")
                .then().assertThat();
    }

    private void then_the_threshold_config_is(int expectedThreshold) {
        response.body("lastMeetingThresholdsInWeeks", Matchers.equalTo(expectedThreshold));
    }

    private void then_there_is_an_error_with(String message) {
        response.statusCode(400)
                .body("message", Matchers.equalTo(   message));
    }
}
