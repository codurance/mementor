package com.codurance.guru.craftspeople;

import com.codurance.guru.GuruApplication;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private Craftsperson craftsperson1;
    private Craftsperson craftsperson2;

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
    public void add_mentor() throws JSONException {
        given_two_craftspeople();

        JSONObject request = new JSONObject();
        request.put("mentorId", craftsperson1.getId());
        request.put("menteeId", craftsperson2.getId());

        RestAssured.given()
                    .contentType("application/json")
                    .body(request.toString())
                    .post("craftspeople/mentor/add")
                .then()
                    .statusCode(204);

        craftspeopleRepository.flush();

        Craftsperson updatedMentor = craftspeopleRepository.findById(craftsperson1.getId()).get();
        Craftsperson updatedMentee = craftspeopleRepository.findById(craftsperson2.getId()).get();

        assertEquals("mentor not found on mentee entity", craftsperson1.getId(), updatedMentee.getMentor().get().getId());
        assertTrue("mentee not found in the mentor's mentees list", updatedMentor.getMentees()
            .stream()
            .map(Craftsperson::getId)
            .anyMatch(actualMenteeId -> craftsperson2.getId().equals(actualMenteeId)));
    }

    private void given_two_craftspeople() {
        craftsperson1 = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        craftsperson2 = craftspeopleRepository.save(new Craftsperson("Ed", "Rixon"));
    }

    private void given_a_craftsperson_with_a_mentor() {
        mentor = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL", mentor));
    }

    private void given_a_craftsperson_in_the_repository() {
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud","CLAUDEL"));
    }

}
