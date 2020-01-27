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

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GuruApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdminIT {

    @LocalServerPort
    int serverPort;

    @Autowired
    private CraftspeopleRepositoryImpl craftspeopleRepository;

    private Craftsperson savedCraftsperson;
    private Craftsperson craftspersonOne;
    private Craftsperson craftspersonTwo;
    private Response response;
    private JSONObject requestBody;

    @Before
    public void setUp() {
        requestBody = new JSONObject();
        RestAssured.port = serverPort;
    }

    @Test
    public void add_a_craftsperson() throws JSONException {
        String firstName = randomString();
        String lastName = randomString();

        given_a_json_with_a_first_name_and_a_last_name_for_a_new_craftsperson(firstName, lastName);

        when_the_post_method_on_the_api_is_called_for_adding_a_craftsperson();

        then_the_craftsperson_has_to_be_saved_in_the_repository(firstName, lastName);
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
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
    public void delete_a_craftsperson() {
        given_a_craftsperson_in_the_repository();

        when_a_craftsperson_is_deleted(savedCraftsperson);
        when_the_get_method_on_the_api_is_called_for_getting_the_deleted_craftsperson();

        then_the_response_should_be_not_found();
    }

    @Test
    public void not_be_allowed_to_duplicate_a_craftperson() throws JSONException {

        given_two_identical_new_craftspeople();

        when_the_post_method_on_the_api_is_called_for_adding_both__identical_craftspeople();

        then_the_response_should_be_bad_request();

        then_the_response_should_contain_the_existing_craftsperson_error();
    }

    private void given_a_craftsperson_in_the_repository() {
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL"));
    }

    private void given_a_json_with_a_first_name_and_a_last_name_for_a_new_craftsperson(String firstName, String lastName) throws JSONException {
        requestBody.put("firstName", firstName);
        requestBody.put("lastName", lastName);
    }

    private void given_a_json_with_a_first_name_and_no_last_name_for_a_new_craftsperson() throws JSONException {
        requestBody.put("firstName", "Arnaldo");
    }

    private void given_a_json_with_no_first_name_and_a_last_name_for_a_new_craftsperson() throws JSONException {
        requestBody.put("lastName", "ARNAUD");
    }

    private void given_two_identical_new_craftspeople() {
        UUID uuid = UUID.randomUUID();
        craftspersonOne = new Craftsperson(uuid.toString(), uuid.toString());
        craftspersonTwo = new Craftsperson(uuid.toString(), uuid.toString());
    }

    private void then_the_craftsperson_has_to_be_saved_in_the_repository(String firstName, String lastName) {
        assertFalse("added craftsperson could not be found", craftspeopleRepository.findByFirstNameAndLastName(firstName, lastName).isEmpty());
    }

    private void then_the_response_should_be_bad_request() {
        response.then().assertThat()
                .statusCode(400);
    }

    private void then_the_response_should_be_not_found() {
        response.then().assertThat()
                .statusCode(404);
    }

    private void then_the_response_should_contain_the_existing_craftsperson_error() {
        response.then().assertThat()
            .body("message", equalTo("Craftsperson already exists."));
    }

    private void when_a_craftsperson_is_deleted(Craftsperson craftsperson) {
        RestAssured.given()
                .delete("craftspeople/{craftspersonId}", craftsperson.getId())
                .then()
                .statusCode(204);
    }

    private void when_the_get_method_on_the_api_is_called_for_getting_the_deleted_craftsperson() {
        response = RestAssured.get("craftspeople/{craftspersonId}", savedCraftsperson.getId());
    }

    private void when_the_post_method_on_the_api_is_called_for_adding_a_craftsperson() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .post("craftspeople/add");
    }

    private void when_the_post_method_on_the_api_is_called_for_adding_both__identical_craftspeople() throws JSONException {
        requestBody.put("firstName", craftspersonOne.getFirstName());
        requestBody.put("lastName", craftspersonOne.getLastName());

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .post("craftspeople/add");

        requestBody.put("firstName", craftspersonTwo.getFirstName());
        requestBody.put("lastName", craftspersonTwo.getLastName());

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .post("craftspeople/add");
    }

}
