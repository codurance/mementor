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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GuruApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MenteeIT {

    @LocalServerPort
    int serverPort;

    @Autowired
    private CraftspeopleRepositoryImpl craftspeopleRepository;

    private Craftsperson savedCraftsperson;
    private Craftsperson mentor;
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
    public void add_mentee() throws JSONException {
        given_two_craftspeople_in_the_repository();
        given_a_json_with_a_mentor_id_and_a_mentee_id();

        when_the_post_method_on_the_api_is_called_to_add_a_mentee_to_a_craftsperson();

        then_the_craftsperson_has_the_mentee();
    }

    @Test
    public void cant_be_mentee_of_oneself() throws JSONException{
        given_a_json_with_a_mentor_and_a_mentee_with_the_same_id();

        when_the_post_method_on_the_api_is_called_to_add_a_mentee_to_a_craftsperson_with_an_invalid_request_body();

        then_the_response_should_be_bad_request();
    }

    @Test
    public void remove_mentee() {
        given_a_craftsperson_with_a_mentor();

        when_you_remove_the_mentor_from_the_craftsperson();

        then_the_craftsperson_should_not_have_a_mentor();
    }

    @Test
    public void retrieve_craftsperson_with_mentees() {
        given_a_craftsperson_with_a_mentor();

        when_the_get_method_on_the_api_is_called_for_the_mentor();

        then_the_craftsperson_retrieved_has_mentees();
    }

    private void given_a_craftsperson_with_a_mentor() {
        mentor = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL", mentor.getId(), Instant.now()));
    }

    private void given_a_json_with_a_mentor_and_a_mentee_with_the_same_id() throws JSONException {
        requestBody.put("mentorId", "1");
        requestBody.put("menteeId", "1");
    }

    private void given_a_json_with_a_mentor_id_and_a_mentee_id() throws JSONException {
        requestBody.put("mentorId", craftspeople.get(0).getId());
        requestBody.put("menteeId", craftspeople.get(1).getId());
    }

    private void given_two_craftspeople_in_the_repository() {
        Craftsperson craftspersonOne = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        Craftsperson craftspersonTwo = craftspeopleRepository.save(new Craftsperson("Ed", "Rixon"));
        craftspeople.add(craftspersonOne);
        craftspeople.add(craftspersonTwo);
        savedCraftsperson = craftspersonOne;
    }

    private void then_the_craftsperson_has_the_mentee() {
        RestAssured.get("craftspeople/{craftspersonId}", craftspeople.get(0).getId())
                .then().assertThat()
                .body("mentees[0].id", equalTo(craftspeople.get(1).getId()));
    }

    private void then_the_craftsperson_retrieved_has_mentees() {
        response.then().assertThat()
                .body("mentees", hasSize(1))
                .body("mentees[0].id", equalTo(savedCraftsperson.getId()))
                .body("mentees[0].firstName", equalTo(savedCraftsperson.getFirstName()))
                .body("mentees[0].lastName", equalTo(savedCraftsperson.getLastName()));
    }

    private void then_the_craftsperson_should_not_have_a_mentor() {
        assertTrue(craftspeopleRepository.findById(savedCraftsperson.getId()).get().getMentorId().isEmpty());
    }

    private void then_the_response_should_be_bad_request() {
        response.then().assertThat()
                .statusCode(400);
    }

    private void when_the_get_method_on_the_api_is_called_for_the_mentor() {
        response = RestAssured.get("craftspeople/{craftspersonId}", mentor.getId());
    }

    private void when_the_post_method_on_the_api_is_called_to_add_a_mentee_to_a_craftsperson() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .put("craftspeople/mentee/add")
                .then()
                .statusCode(204)
                .extract()
                .response();
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

    private void when_you_remove_the_mentor_from_the_craftsperson() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .put("craftspeople/mentee/remove/{craftspersonId}", savedCraftsperson.getId())
                .then()
                .statusCode(204)
                .extract()
                .response();
    }

}
