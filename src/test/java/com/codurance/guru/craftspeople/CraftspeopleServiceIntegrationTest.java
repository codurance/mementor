package com.codurance.guru.craftspeople;

import com.codurance.guru.GuruApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GuruApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CraftspeopleServiceIntegrationTest {

    @Autowired
    CraftspeopleService craftspeopleService;

    @Autowired
    CraftspeopleRepository craftspeopleRepository;



    private Craftsperson mentor;
    private Craftsperson mentee;
    private Craftsperson savedCraftsperson;
    private Craftsperson actualCraftsperson;

    @Test
    public void retrieve_a_craftsperson_when_asked_for_one() {
        given_a_new_craftsperson_that_is_saved_into_the_repository();

        when_we_retrieve_the_id_for_that_craftsperson();

        then_it_should_return_the_right_craftsperson();
    }

    @Test
    public void deleting_a_mentor_should_not_delete_its_mentees() {
        given_a_craftsperson_with_a_mentor();

        when_the_mentor_is_removed_from_the_repository();

        then_the_mentee_should_still_exist_in_the_repository();
    }

    private void then_the_mentee_should_still_exist_in_the_repository() {
        assertTrue("mentee has been removed", craftspeopleRepository.existsById(mentee.getId()));
    }

    private void when_the_mentor_is_removed_from_the_repository() {
        craftspeopleService.deleteCraftsperson(mentor.getId());
    }

    private void then_it_should_return_the_right_craftsperson() {
        assertEquals(savedCraftsperson.getId(), actualCraftsperson.getId());
        assertEquals(savedCraftsperson.getFirstName(), actualCraftsperson.getFirstName());
        assertEquals(savedCraftsperson.getLastName(), actualCraftsperson.getLastName());
    }

    private void when_we_retrieve_the_id_for_that_craftsperson() {
        actualCraftsperson = craftspeopleService.retrieveCraftsperson(savedCraftsperson.getId()).get();
    }

    private void given_a_new_craftsperson_that_is_saved_into_the_repository() {
        savedCraftsperson = craftspeopleRepository.save(new Craftsperson("John", "Doe"));
    }

    private void given_a_craftsperson_with_a_mentor() {
        mentor = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        mentee = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL", mentor));
    }
}
