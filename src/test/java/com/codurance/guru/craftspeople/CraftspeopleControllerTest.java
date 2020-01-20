package com.codurance.guru.craftspeople;

import com.codurance.guru.GuruApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hibernate.id.GUIDGenerator;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GuruApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CraftspeopleControllerTest {

    @Autowired
    private CraftspeopleRepository craftspeopleRepository;

    private Craftsperson savedCraftsperson;
    private Craftsperson mentor;
    private Craftsperson craftspersonOne;
    private Craftsperson craftspersonTwo;
    private List<Craftsperson> craftspeople;
    private Response response;
    private JSONObject requestBody;
    private Integer savedId;

    @Before
    public void setUp() {
        requestBody = new JSONObject();
        craftspeople = new ArrayList<>();
    }

    @Test
    public void retrieve_a_craftsperson() {
        given_a_craftsperson_in_the_repository();

        when_the_get_method_on_the_api_is_called_with_the_craftsperson_id();

        then_the_returned_craftsperson_is_the_correct_one();
    }

    @Test
    public void retrieve_craftsperson_with_a_mentor() {
        given_a_craftsperson_with_a_mentor();

        when_the_get_method_on_the_api_is_called_with_the_craftsperson_id();

        then_the_craftsperson_retrieved_has_a_mentor();
    }

    @Test
    public void retrieve_craftsperson_with_mentees() {
        given_a_craftsperson_with_a_mentor();

        when_the_get_method_on_the_api_is_called_for_the_mentor();

        then_the_craftsperson_retrieved_has_mentees();
    }

    @Test
    public void retrieve_all_craftspeople() {
        when_the_get_method_on_the_api_is_called_for_all_craftspeople();

        then_the_size_of_the_array_returned_is_the_amount_of_craftspeople_in_the_company();
    }

    @Test
    public void delete_a_craftsperson() {
        given_a_craftsperson_in_the_repository();

        when_a_craftsperson_is_deleted(savedCraftsperson);
        when_the_get_method_on_the_api_is_called_for_getting_the_deleted_craftsperson();

        then_the_response_should_be_not_found();
    }

    @Test
    public void add_a_craftsperson() throws JSONException {
        given_a_json_with_a_first_name_and_a_last_name_for_a_new_craftsperson();

        when_the_post_method_on_the_api_is_called_for_adding_a_craftsperson();

        then_the_craftsperson_has_to_be_saved_in_the_repository();
    }

    @Test
    public void add_mentee() throws JSONException {
        given_two_craftspeople_in_the_repository();
        given_a_json_with_a_mentor_id_and_a_mentee_id();

        when_the_post_method_on_the_api_is_called_to_add_a_mentee_to_a_craftsperson();

        then_the_craftsperson_has_the_mentee();
    }

    @Test
    public void remove_mentee() {
        given_a_craftsperson_with_a_mentor();

        when_you_remove_the_mentor_from_the_craftsperson();

        then_the_craftsperson_should_not_have_a_mentor();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
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
    public void cant_add_a_craftsperson_with_no_first_name() throws JSONException {
        given_a_json_with_no_first_name_and_a_last_name_for_a_new_craftsperson();

        when_the_post_method_on_the_api_is_called_for_adding_a_craftsperson();

        then_the_response_should_be_bad_request();
    }

    @Test
    public void cant_add_a_craftsperson_with_no_last_name() throws JSONException {
        given_a_json_with_a_first_name_and_no_last_name_for_a_new_craftsperson();

        when_the_post_method_on_the_api_is_called_for_adding_a_craftsperson();

        then_the_response_should_be_bad_request();
    }

    @Test
    public void cant_add_a_craftsperson_with_no_name() {
        when_the_post_method_on_the_api_is_called_for_adding_a_craftsperson();

        then_the_response_should_be_bad_request();
    }

    @Test
    public void not_be_allowed_to_duplicate_a_craftperson() throws JSONException {

        given_two_identical_new_craftspeople();

        when_the_post_method_on_the_api_is_called_for_adding_both__identical_craftspeople();

        when_the_get_method_is_called_to_query_the_added_craftsperson();

        then_there_will_be_only_one_person();
    }

    private void when_the_post_method_on_the_api_is_called_for_adding_both__identical_craftspeople() throws JSONException {
        requestBody.put("firstName", craftspersonOne.getFirstName());
        requestBody.put("lastName", craftspersonOne.getLastName());

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .post("craftspeople/add");

        savedId = Integer.parseInt(response.asString());

        requestBody.put("firstName", craftspersonTwo.getFirstName());
        requestBody.put("lastName", craftspersonTwo.getLastName());

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .post("craftspeople/add");
    }

    private void given_two_identical_new_craftspeople() {
        UUID uuid = UUID.randomUUID();
        craftspersonOne = new Craftsperson(uuid.toString(), uuid.toString());
        craftspersonTwo = new Craftsperson(uuid.toString(), uuid.toString());
    }

    private void when_the_get_method_is_called_to_query_the_added_craftsperson(){
        craftspeople = craftspeopleRepository.findByFirstNameAndLastName(craftspersonOne.getFirstName(), craftspersonOne.getLastName());
    }

    private void then_there_will_be_only_one_person() {
        assertEquals(1, craftspeople.size());
        assertEquals(savedId, craftspeople.get(0).getId());
        response.then().assertThat()
                .statusCode(409);
//                .body("message", equalTo("This craftsperson already exists"));
    }

    private void then_the_response_should_be_bad_request() {
        response.then().assertThat()
                .statusCode(400);
    }

    private void then_the_relationship_between_mentor_and_mentee_is_lost(Craftsperson updatedMentor, Craftsperson updatedMentee) {
        assertTrue(updatedMentee.getMentor().isPresent());
        assertEquals(craftspersonOne.getId(), updatedMentee.getMentor().get().getId());
        assertTrue("mentee not found in the mentor's mentees list", updatedMentor.getMentees()
                .stream()
                .map(Craftsperson::getId)
                .anyMatch(actualMenteeId -> craftspersonTwo.getId().equals(actualMenteeId)));
    }

    private void when_the_post_method_on_the_api_is_called_to_add_a_mentor_to_a_craftsperson() {
        given()
                .contentType("application/json")
                .body(requestBody.toString())
                .post("craftspeople/mentor/add")
                .then()
                .statusCode(204);

        craftspeopleRepository.flush();
    }

    private void then_the_craftsperson_should_not_have_a_mentor() {
        //noinspection OptionalGetWithoutIsPresent
        assertTrue(craftspeopleRepository.findById(savedCraftsperson.getId()).get().getMentor().isEmpty());
    }

    private void when_you_remove_the_mentor_from_the_craftsperson() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .put("craftspeople/mentee/remove/{craftspersonId}", savedCraftsperson.getId())
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    private void then_the_craftsperson_has_the_mentee() {
        RestAssured.get("craftspeople/{craftspersonId}", craftspeople.get(0).getId())
                .then().assertThat()
                .body("mentees[0].id", equalTo(craftspeople.get(1).getId()));
    }

    private void when_the_post_method_on_the_api_is_called_to_add_a_mentee_to_a_craftsperson() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .put("craftspeople/mentee/add")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    private void given_a_json_with_a_mentor_id_and_a_mentee_id() throws JSONException {
        requestBody.put("mentorId", craftspeople.get(0).getId());
        requestBody.put("menteeId", craftspeople.get(1).getId());
    }

    private void when_the_post_method_on_the_api_is_called_for_adding_a_craftsperson() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .post("craftspeople/add");
    }

    private void then_the_response_should_be_not_found() {
        response.then().assertThat()
                .statusCode(404);
    }

    private void when_the_get_method_on_the_api_is_called_for_getting_the_deleted_craftsperson() {
        response = RestAssured.get("craftspeople/{craftspersonId}", savedCraftsperson.getId());
    }

    private void then_the_size_of_the_array_returned_is_the_amount_of_craftspeople_in_the_company() {
        long craftspeopleCount = craftspeopleRepository.count();
        response.then().assertThat()
                .body("$", hasSize((int) craftspeopleCount));
    }

    private void when_the_get_method_on_the_api_is_called_for_all_craftspeople() {
        response = RestAssured.get("craftspeople");
    }

    private void then_the_craftsperson_retrieved_has_mentees() {
        response.then().assertThat()
                .body("mentees", hasSize(1))
                .body("mentees[0].id", equalTo(savedCraftsperson.getId()))
                .body("mentees[0].firstName", equalTo(savedCraftsperson.getFirstName()))
                .body("mentees[0].lastName", equalTo(savedCraftsperson.getLastName()));
    }

    private void when_the_get_method_on_the_api_is_called_for_the_mentor() {
        response = RestAssured.get("craftspeople/{craftspersonId}", mentor.getId());
    }

    private void then_the_craftsperson_retrieved_has_a_mentor() {
        response.then().assertThat()
                .body("id", equalTo(savedCraftsperson.getId()))
                .body("mentor.firstName", equalTo(mentor.getFirstName()))
                .body("mentor.lastName", equalTo(mentor.getLastName()))
                .body("mentor.id", equalTo(mentor.getId()))
                .body("lastMeeting", equalTo((int)savedCraftsperson.getLastMeeting().get().getEpochSecond()));
    }

    private void when_the_get_method_on_the_api_is_called_with_the_craftsperson_id() {
        response = RestAssured.get("craftspeople/{craftspersonId}", savedCraftsperson.getId());
    }

    private void then_the_returned_craftsperson_is_the_correct_one() {
        response.then().assertThat()
                .body("id", equalTo(savedCraftsperson.getId()))
                .body("firstName", equalTo(savedCraftsperson.getFirstName()))
                .body("lastName", equalTo(savedCraftsperson.getLastName()));
    }

    private void then_the_craftsperson_has_to_be_saved_in_the_repository() {

        Integer newId = Integer.parseInt(response.asString());

        response = RestAssured.get("craftspeople/{craftspersonId}", newId);

        response.then().assertThat()
                .body("firstName", equalTo("Arnaldo"))
                .body("lastName", equalTo("ARNAUD"))
                .statusCode(200);
    }

    private void given_a_json_with_a_first_name_and_a_last_name_for_a_new_craftsperson() throws JSONException {
        requestBody.put("firstName", "Arnaldo");
        requestBody.put("lastName", "ARNAUD");
    }

    private void given_a_json_with_no_first_name_and_a_last_name_for_a_new_craftsperson() throws JSONException {
        requestBody.put("lastName", "ARNAUD");
    }

    private void given_a_json_with_a_first_name_and_no_last_name_for_a_new_craftsperson() throws JSONException {
        requestBody.put("firstName", "Arnaldo");
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
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

        craftspeopleRepository.flush();

        Craftsperson updatedMentor = craftspeopleRepository.findById(mentor.getId()).get();
        Craftsperson updatedMentee = craftspeopleRepository.findById(savedCraftsperson.getId()).get();

        assertTrue("mentor was not removed", updatedMentee.getMentor().isEmpty());
        assertTrue("mentee is still in the mentor's mentees list", updatedMentor.getMentees()
                .stream()
                .map(Craftsperson::getId)
                .noneMatch(savedCraftsperson.getId()::equals));
    }

    private void when_a_craftsperson_is_deleted(Craftsperson craftsperson) {
        RestAssured.given()
                .delete("craftspeople/{craftspersonId}", craftsperson.getId())
                .then()
                .statusCode(200);
    }

    private void given_two_craftspeople_in_the_repository() {
        craftspersonOne = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        craftspersonTwo = craftspeopleRepository.save(new Craftsperson("Ed", "Rixon"));
        craftspeople.add(craftspersonOne);
        craftspeople.add(craftspersonTwo);
        savedCraftsperson = craftspersonOne;
    }

    private void given_a_craftsperson_with_a_mentor() {
        mentor = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL", mentor, Instant.now()));
    }

    private void given_a_craftsperson_in_the_repository() {
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL"));
    }

}










