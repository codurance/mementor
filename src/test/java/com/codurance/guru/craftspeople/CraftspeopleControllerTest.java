package com.codurance.guru.craftspeople;

import com.codurance.guru.GuruApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
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
    private List<Craftsperson> craftspeople = new ArrayList<>();

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
    public void add_mentee() {
        given_two_craftspeople();

        String payload = "{\n" +
                "  \"mentorId\": \"" +
                craftspeople.get(0).getId() +
                "\",\n" +
                "  \"menteeId\": \"" +
                craftspeople.get(1).getId() +
                "\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .put("/craftspeople/addmentee")
                .then()
                .statusCode(200)
                .extract()
                .response();


        int testMenteeId = craftspeople.get(1).getId();
        Optional<Craftsperson> dbMentee = craftspeopleRepository.findById(testMenteeId);

        assertEquals(craftspeople.get(0).getId(), dbMentee.get().getMentor().get().getId());
    }

    @Test
    public void remove_mentee() {
        given_a_craftsperson_with_a_mentor();

        given()
                .contentType(ContentType.JSON)
                .put("craftspeople/mentee/remove/" + savedCraftsperson.getId())
                .then()
                .statusCode(200)
                .extract()
                .response();

        assertEquals(Optional.empty(),craftspeopleRepository.findById(savedCraftsperson.getId()).get().getMentor());
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
        Craftsperson craftpersonOne = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        Craftsperson craftpersonTwo = craftspeopleRepository.save(new Craftsperson("Ed", "Rixon"));
        craftspeople.add(craftpersonOne);
        craftspeople.add(craftpersonTwo);
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
