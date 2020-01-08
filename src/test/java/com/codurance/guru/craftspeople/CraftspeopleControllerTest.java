package com.codurance.guru.craftspeople;

import com.codurance.guru.GuruApplication;
import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    private void given_two_craftspeople() {
        craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        craftspeopleRepository.save(new Craftsperson("Ed", "Rixon"));
    }

    private void given_a_craftsperson_with_a_mentor() {
        mentor = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL", mentor));
    }

    private void given_a_craftsperson_in_the_repository() {
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud","CLAUDEL"));
    }

}
