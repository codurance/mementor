package com.codurance.guru.infra;

import com.codurance.guru.GuruApplication;
import com.codurance.guru.core.craftspeople.Craftsperson;
import com.codurance.guru.infra.persistence.repository.CraftspeopleRepositoryImpl;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static io.restassured.RestAssured.*;
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

    @Before
    public void setUp() {
        port = serverPort;
    }

    @Test
    public void retrieve_a_craftsperson() {
        given_a_craftsperson_in_the_repository();

        when().get("craftspeople/{craftspersonId}", savedCraftsperson.getId())
                .then().assertThat()
                .body("id", equalTo(savedCraftsperson.getId()))
                .body("firstName", equalTo(savedCraftsperson.getFirstName()))
                .body("lastName", equalTo(savedCraftsperson.getLastName()));
    }

    @Test
    public void retrieve_all_craftspeople() {
        long craftspeopleCount = craftspeopleRepository.count();

        get("craftspeople")
                .then().assertThat()
                .body("$", hasSize((int) craftspeopleCount));
    }

    private void given_a_craftsperson_in_the_repository() {
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL"));
    }

}
