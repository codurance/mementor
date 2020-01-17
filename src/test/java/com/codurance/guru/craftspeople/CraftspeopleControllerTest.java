package com.codurance.guru.craftspeople;

import com.codurance.guru.GuruApplication;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    CraftspeopleRepository craftspeopleRepository;

    private Craftsperson savedCraftsperson;
    private Craftsperson mentor;
    private Craftsperson craftpersonOne;
    private Craftsperson craftpersonTwo;
    private List<Craftsperson> craftspeople;
    private Response response;
    private JSONObject requestBody;

    @Before
    public void setUp() throws Exception {
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

        then_the_craftsperson_should_not_exist();
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

        then_the_craftsperson_should_be_empty();
    }

    @Test
    public void add_mentor() throws JSONException {
        given_two_craftspeople_in_the_repository();
        given_a_json_with_a_mentor_id_and_a_mentee_id();

        when_the_post_method_on_the_api_is_called_to_add_a_mentor_to_a_craftsperson();

        Craftsperson updatedMentor = craftspeopleRepository.findById(craftpersonOne.getId()).get();
        Craftsperson updatedMentee = craftspeopleRepository.findById(craftpersonTwo.getId()).get();

        then_the_relationship_between_mentor_and_mentee_is_lost(updatedMentor, updatedMentee);
    }

    private void then_the_relationship_between_mentor_and_mentee_is_lost(Craftsperson updatedMentor, Craftsperson updatedMentee) {
        assertEquals("mentor not found on mentee entity", craftpersonOne.getId(), updatedMentee.getMentor().get().getId());
        assertTrue("mentee not found in the mentor's mentees list", updatedMentor.getMentees()
                .stream()
                .map(Craftsperson::getId)
                .anyMatch(actualMenteeId -> craftpersonTwo.getId().equals(actualMenteeId)));
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

    private void then_the_craftsperson_should_be_empty() {
        assertEquals(Optional.empty(), craftspeopleRepository.findById(savedCraftsperson.getId()).get().getMentor());
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
                .put("craftspeople/addmentee")
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

    private void then_the_craftsperson_should_not_exist() {
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
                .body("mentor.firstName", equalTo(mentor.getFirstName()))
                .body("mentor.lastName", equalTo(mentor.getLastName()))
                .body("mentor.id", equalTo(mentor.getId()));
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

    private void when_a_craftsperson_is_deleted(Craftsperson craftsperson) {
        craftspeopleRepository.deleteById(craftsperson.getId());
    }

    private void given_two_craftspeople_in_the_repository() {
        craftpersonOne = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        craftpersonTwo = craftspeopleRepository.save(new Craftsperson("Ed", "Rixon"));
        craftspeople.add(craftpersonOne);
        craftspeople.add(craftpersonTwo);
        savedCraftsperson = craftpersonOne;
    }

    private void given_a_craftsperson_with_a_mentor() {
        mentor = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL", mentor));
    }

    private void given_a_craftsperson_in_the_repository() {
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL"));
    }

}
