package com.codurance.guru.craftspeople;

import com.codurance.guru.GuruApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GuruApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CraftspeopleControllerTest {

    @Autowired
    CraftspeopleRepository craftspeopleRepository;

    private Craftsperson savedCraftsperson;
    private Craftsperson mentor;
    private Response response;

    @Test
    public void retrieve_a_craftsperson() {
        given_a_craftsperson_in_the_repository();

        RestAssured.get("craftspeople/{craftspersonId}", savedCraftsperson.getId())
                .then().assertThat()
                .body("id", equalTo(savedCraftsperson.getId()))
                .body("firstName", equalTo(savedCraftsperson.getFirstName()))
                .body("lastName", equalTo(savedCraftsperson.getLastName()));
    }

    @Test
    public void retrieve_craftsperson_with_a_mentor() {
        given_a_craftsperson_with_a_mentor();

        RestAssured.get("craftspeople/{craftspersonId}", savedCraftsperson.getId())
                .then().assertThat()
                .body("mentor.firstName", equalTo(mentor.getFirstName()))
                .body("mentor.lastName", equalTo(mentor.getLastName()))
                .body("mentor.id", equalTo(mentor.getId()));
    }

    @Test
    public void retrieve_craftsperson_with_mentees() {
        given_a_craftsperson_with_a_mentor();

        RestAssured.get("craftspeople/{craftspersonId}", mentor.getId())
                .then().assertThat()
                .body("mentees", hasSize(1))
                .body("mentees[0].id", equalTo(savedCraftsperson.getId()))
                .body("mentees[0].firstName", equalTo(savedCraftsperson.getFirstName()))
                .body("mentees[0].lastName", equalTo(savedCraftsperson.getLastName()));
    }

    @Test
    public void retrieve_all_craftspeople() {
        given_two_craftspeople();
        long craftspeopleCount = craftspeopleRepository.count();

        RestAssured.get("craftspeople")
                .then().assertThat()
                .body("$", hasSize((int) craftspeopleCount));
    }

    @Test
    public void delete_a_craftsperson() {
        given_two_craftspeople();

        when_a_craftsperson_is_deleted(savedCraftsperson);

        RestAssured.get("craftspeople/{craftspersonId}", savedCraftsperson.getId())
                .then().assertThat()
                .statusCode(404);
    }

    @Test
    public void add_a_craftsperson() throws JSONException {
        given_an_api_call_with_a_first_name_and_a_last_name();

        then_the_craftsperson_has_to_be_saved_in_the_repository();
    }

    private void then_the_craftsperson_has_to_be_saved_in_the_repository(){

        Integer newId = Integer.parseInt(response.asString());

        RestAssured.get("craftspeople/{craftspersonId}", newId)
                .then().assertThat()
                .body("firstName", equalTo("Arnaldo"))
                .body("lastName", equalTo("ARNAUD"))
                .statusCode(200);
    }

    private void given_an_api_call_with_a_first_name_and_a_last_name() throws JSONException {
        JSONObject requestBody = new JSONObject();

        requestBody.put("firstName", "Arnaldo");
        requestBody.put("lastName", "ARNAUD");

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .post("craftspeople/add");
    }

    private void when_a_craftsperson_is_deleted(Craftsperson craftsperson) {
        craftspeopleRepository.deleteById(craftsperson.getId());
    }

    private void given_two_craftspeople() {
        craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Ed", "Rixon"));
    }

    private void given_a_craftsperson_with_a_mentor() {
        mentor = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL", mentor));
    }

    private void given_a_craftsperson_in_the_repository() {
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL"));
    }

}
