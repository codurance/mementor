package com.codurance.guru.infra;

import com.codurance.guru.GuruApplication;
import com.codurance.guru.core.craftspeople.Craftsperson;
import com.codurance.guru.infra.persistence.repository.CraftspeopleRepositoryImpl;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

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
    private Response response;

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
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

    private void then_the_craftsperson_retrieved_has_a_mentor() {
        response.then().assertThat()
                .body("id", equalTo(savedCraftsperson.getId()))
                .body("mentor.firstName", equalTo(mentor.getFirstName()))
                .body("mentor.lastName", equalTo(mentor.getLastName()))
                .body("mentor.id", equalTo(mentor.getId()))
                .body("lastMeeting", equalTo((int)savedCraftsperson.getLastMeeting().get().getEpochSecond()));
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

}
