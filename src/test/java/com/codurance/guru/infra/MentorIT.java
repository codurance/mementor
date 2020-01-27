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

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GuruApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MentorIT {

    @LocalServerPort
    int serverPort;

    @Autowired
    private CraftspeopleRepositoryImpl craftspeopleRepository;

    private Craftsperson savedCraftsperson;
    private Craftsperson mentor;
    private Craftsperson craftspersonOne;
    private Craftsperson craftspersonTwo;
    private List<Craftsperson> craftspeople;
    private Response response;
    private JSONObject requestBody;

    @Before
    public void setUp() {
        requestBody = new JSONObject();
        craftspeople = new ArrayList<>();
        RestAssured.port = serverPort;
    }

    @Test
    public void add_mentor() throws JSONException {
        given_two_craftspeople_in_the_repository();
        given_a_json_with_a_mentor_id_and_a_mentee_id();

        when_the_post_method_on_the_api_is_called_to_add_a_mentor_to_a_craftsperson();

        Craftsperson updatedMentor = craftspeopleRepository.findById(craftspersonOne.getId()).get();
        Craftsperson updatedMentee = craftspeopleRepository.findById(craftspersonTwo.getId()).get();

        then_the_relationship_between_mentor_and_mentee_is_lost(updatedMentor, updatedMentee);
    }

    @Test
    public void cant_have_duplicate_mentees_as_a_mentor() throws JSONException{
        given_a_craftsperson_with_a_mentor();
        given_a_json_with_a_mentor_id_and_a_mentee_id(mentor.getId(), savedCraftsperson.getId());

        when_the_post_method_on_the_api_is_called_to_add_a_mentee_to_a_craftsperson_with_an_invalid_request_body();

        then_the_response_should_be_bad_request();
    }

    @Test
    public void remove_mentor() throws JSONException {
        given_a_craftsperson_with_a_mentor();

        JSONObject request = new JSONObject();
        request.put("menteeId", savedCraftsperson.getId());

        RestAssured.given()
                .contentType("application/json")
                .body(request.toString())
                .post("craftspeople/mentor/remove")
                .then()
                .statusCode(204);

        Craftsperson updatedMentor = craftspeopleRepository.findById(mentor.getId()).get();
        Craftsperson updatedMentee = craftspeopleRepository.findById(savedCraftsperson.getId()).get();

        assertTrue("mentor was not removed", updatedMentee.getMentorId().isEmpty());
        assertTrue("mentee is still in the mentor's mentees list", updatedMentor.getMenteeIds()
                .stream()
                .noneMatch(savedCraftsperson.getId()::equals));
    }

    @Test
    public void retrieve_craftsperson_with_a_mentor() {
        given_a_craftsperson_with_a_mentor();

        get("craftspeople/{craftspersonId}", savedCraftsperson.getId())
                .then().assertThat()
                .body("id", equalTo(savedCraftsperson.getId()))
                .body("mentor.firstName", equalTo(mentor.getFirstName()))
                .body("mentor.lastName", equalTo(mentor.getLastName()))
                .body("mentor.id", equalTo(mentor.getId()))
                .body("lastMeeting", equalTo((int)savedCraftsperson.getLastMeeting().get().getEpochSecond()));
    }

    private void given_a_craftsperson_with_a_mentor() {
        mentor = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL", mentor.getId(), Instant.now()));
    }

    private void given_a_json_with_a_mentor_id_and_a_mentee_id() throws JSONException {
        requestBody.put("mentorId", craftspeople.get(0).getId());
        requestBody.put("menteeId", craftspeople.get(1).getId());
    }

    private void given_a_json_with_a_mentor_id_and_a_mentee_id(int mentorId, int menteeId) throws JSONException {
        requestBody.put("mentorId", mentorId);
        requestBody.put("menteeId", menteeId);
    }

    private void given_two_craftspeople_in_the_repository() {
        craftspersonOne = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        craftspersonTwo = craftspeopleRepository.save(new Craftsperson("Ed", "Rixon"));
        craftspeople.add(craftspersonOne);
        craftspeople.add(craftspersonTwo);
        savedCraftsperson = craftspersonOne;
    }

    private void then_the_relationship_between_mentor_and_mentee_is_lost(Craftsperson updatedMentor, Craftsperson updatedMentee) {
        assertTrue(updatedMentee.getMentorId().isPresent());
        assertEquals(craftspersonOne.getId(), updatedMentee.getMentorId().get());
        assertTrue("mentee not found in the mentor's mentees list", updatedMentor.getMenteeIds()
                .stream()
                .anyMatch(actualMenteeId -> craftspersonTwo.getId().equals(actualMenteeId)));
    }

    private void then_the_response_should_be_bad_request() {
        response.then().assertThat()
                .statusCode(400);
    }

    private void when_the_post_method_on_the_api_is_called_to_add_a_mentee_to_a_craftsperson_with_an_invalid_request_body() {
        response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(requestBody.toString())
            .put("craftspeople/mentee/add")
            .then()
            .statusCode(400)
            .extract()
            .response();
    }

    private void when_the_post_method_on_the_api_is_called_to_add_a_mentor_to_a_craftsperson() {
        given()
                .contentType("application/json")
                .body(requestBody.toString())
                .post("craftspeople/mentor/add")
                .then()
                .statusCode(204);
    }

}
