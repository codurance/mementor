package com.codurance.guru.craftspeople;

import com.codurance.guru.GuruApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GuruApplication.class)
public class CraftspeopleServiceTest {

    @Autowired
    CraftspeopleService craftspeopleService;

    @MockBean
    CraftspeopleRepository craftspeopleRepository;

    @Test
    public void retrieve_a_craftsperson_when_asked_for_one() {
        Optional<Craftsperson> craftsperson = Optional.of(new Craftsperson("John", "Doe"));
        when(craftspeopleRepository.findById(1)).thenReturn(craftsperson);

        assertEquals(craftsperson, craftspeopleService.retrieveCraftsperson(1));
    }
}
