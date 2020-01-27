package com.codurance.guru.infra;

import com.codurance.guru.GuruApplication;
import com.codurance.guru.core.craftspeople.Craftsperson;
import com.codurance.guru.infra.persistence.repository.CraftspeopleRepositoryImpl;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GuruApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CraftspeopleIT {

    @LocalServerPort
    int serverPort;

    @Autowired
    private CraftspeopleRepositoryImpl craftspeopleRepository;

    private Craftsperson savedCraftsperson;
    private Craftsperson mentor;
    private List<Craftsperson> craftspeople;
    private Response response;
    private JSONObject requestBody;
    private int lastMeetingEpoch;

    @Before
    public void setUp() {
        requestBody = new JSONObject();
        craftspeople = new ArrayList<>();
        lastMeetingEpoch = 1500000000;
        RestAssured.port = serverPort;
    }

    @Test
    public void can_update_a_craftsperson_last_meeting() throws JSONException {
        given_a_craftsperson_with_a_mentor();
        given_the_request_body_has_a_last_meeting_set();

        when_the_last_meeting_is_set();

        then_the_response_should_be_no_content();
        then_the_last_meeting_is_updated();
    }

    @Test
    public void cant_update_a_craftsperson_last_meeting_with_a_date_in_future() throws JSONException {
        given_a_craftsperson_with_a_mentor();
        given_the_request_body_has_a_last_meeting_set_in_the_future();

        when_the_last_meeting_is_set();

        then_the_response_should_be_bad_request();
        then_the_response_should_contain_the_last_meeting_error();
    }

    @Test
    public void retrieve_a_craftsperson() {
        given_a_craftsperson_in_the_repository();

        when_the_get_method_on_the_api_is_called_with_the_craftsperson_id();

        then_the_returned_craftsperson_is_the_correct_one();
    }

    @Test
    public void retrieve_all_craftspeople() {
        when_the_get_method_on_the_api_is_called_for_all_craftspeople();

        then_the_size_of_the_array_returned_is_the_amount_of_craftspeople_in_the_company();
    }

    @Test
    public void retrieve_craftsperson_with_a_mentor() {
        given_a_craftsperson_with_a_mentor();

        when_the_get_method_on_the_api_is_called_with_the_craftsperson_id();

        then_the_craftsperson_retrieved_has_a_mentor();
    }

    private void given_a_craftsperson_in_the_repository() {
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL"));
    }

    private void given_a_craftsperson_with_a_mentor() {
        mentor = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL", mentor.getId(), Instant.now()));
    }

    private void given_the_request_body_has_a_last_meeting_set() throws JSONException {
        requestBody.put("craftspersonId", savedCraftsperson.getId());
        requestBody.put("lastMeeting", lastMeetingEpoch);
    }

    private void given_the_request_body_has_a_last_meeting_set_in_the_future() throws JSONException {
        requestBody.put("craftspersonId", savedCraftsperson.getId());
        requestBody.put("lastMeeting", Instant.now().plus(2, ChronoUnit.DAYS).getEpochSecond());
    }

    private void then_the_craftsperson_retrieved_has_a_mentor() {
        response.then().assertThat()
                .body("id", equalTo(savedCraftsperson.getId()))
                .body("mentor.firstName", equalTo(mentor.getFirstName()))
                .body("mentor.lastName", equalTo(mentor.getLastName()))
                .body("mentor.id", equalTo(mentor.getId()))
                .body("lastMeeting", equalTo((int)savedCraftsperson.getLastMeeting().get().getEpochSecond()));
    }

    private void then_the_last_meeting_is_updated() {
        Optional<Instant> lastMeeting = craftspeopleRepository.findById(savedCraftsperson.getId()).get().getLastMeeting();
        assertTrue(lastMeeting.isPresent());
        assertEquals(Instant.ofEpochSecond(lastMeetingEpoch), lastMeeting.get());
    }

    private void then_the_response_should_be_bad_request() {
        response.then().assertThat()
                .statusCode(400);
    }

    private void then_the_response_should_be_no_content() {
        response.then().assertThat()
                .statusCode(204);
    }

    private void then_the_response_should_contain_the_last_meeting_error() {
        response.then().assertThat()
                .body("message", equalTo("The last meeting date is too far in the future"));
    }

    private void then_the_returned_craftsperson_is_the_correct_one() {
        response.then().assertThat()
                .body("id", equalTo(savedCraftsperson.getId()))
                .body("firstName", equalTo(savedCraftsperson.getFirstName()))
                .body("lastName", equalTo(savedCraftsperson.getLastName()));
    }

    private void then_the_size_of_the_array_returned_is_the_amount_of_craftspeople_in_the_company() {
        long craftspeopleCount = craftspeopleRepository.count();
        response.then().assertThat()
                .body("$", hasSize((int) craftspeopleCount));
    }

    private void when_the_get_method_on_the_api_is_called_for_all_craftspeople() {
        response = RestAssured.get("craftspeople");
    }

    private void when_the_get_method_on_the_api_is_called_with_the_craftsperson_id() {
        response = RestAssured.get("craftspeople/{craftspersonId}", savedCraftsperson.getId());
    }

    private void when_the_last_meeting_is_set() {
        response = given()
                .contentType("application/json")
                .body(requestBody.toString())
                .put("craftspeople/lastmeeting");
    }

}
