package com.codurance.guru.craftspeople;

import com.codurance.guru.GuruApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GuruApplication.class)
public class CraftspeopleServiceTest {

    @Autowired
    CraftspeopleService craftspeopleService;

    @Autowired
    CraftspeopleRepository craftspeopleRepository;

    private Craftsperson mentor;
    private Craftsperson mentee;

    @Test
    public void retrieve_a_craftsperson_when_asked_for_one() {
        Craftsperson savedCraftsperson = craftspeopleRepository.save(new Craftsperson("John", "Doe"));
        Craftsperson actualCraftsperson = craftspeopleService.retrieveCraftsperson(savedCraftsperson.getId()).get();

        assertEquals(savedCraftsperson.getId(), actualCraftsperson.getId());
        assertEquals(savedCraftsperson.getFirstName(), actualCraftsperson.getFirstName());
        assertEquals(savedCraftsperson.getLastName(), actualCraftsperson.getLastName());
    }

    @Test
    public void deleting_a_mentor_shouldnt_delete_its_mentees() {
        given_a_craftsperson_with_a_mentor();

        craftspeopleService.deleteCraftsperson(mentor.getId());

        assertTrue("mentee has been removed", craftspeopleRepository.existsById(mentee.getId()));
    }

    private void given_a_craftsperson_with_a_mentor() {
        mentor = craftspeopleRepository.save(new Craftsperson("Jose", "Wenzel"));
        mentee = craftspeopleRepository.save(new Craftsperson("Arnaud", "CLAUDEL", mentor));
    }
}
