package com.codurance.guru.core.craftspeople;

import com.codurance.guru.core.craftspeople.exceptions.DuplicateMenteeException;
import com.codurance.guru.core.craftspeople.exceptions.InvalidMentorRelationshipException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CraftspeopleValidatorUnitTest {

    @Mock
    private CraftspeopleRepository repository;

    private CraftspeopleValidator validator;

    @Before
    public void setUp(){
        validator = new CraftspeopleValidator(repository);
    }

    @Test(expected = InvalidMentorRelationshipException.class)
    public void cant_add_mentor_to_oneself() {
        validator.validateSetMentee(1, 1);
    }

    @Test(expected = DuplicateMenteeException.class)
    public void cant_add_duplicate_mentee_to_craftsperson() {
        Craftsperson craftsperson = new Craftsperson("ed", "rixon", List.of(3));
        Craftsperson mentee = new Craftsperson(3, "giulio", "peps", craftsperson.getId());

        BDDMockito.given(repository.findById(2)).willReturn(Optional.of(craftsperson));

        validator.validateSetMentee(2, mentee.getId());
    }
}