package com.codurance.guru.core.craftspeople;

import com.codurance.guru.core.craftspeople.exceptions.InvalidLastMeetingDateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class CraftspeopleServiceUnitTest {

    private static final int ID = 1;
    @Mock
    private CraftspeopleRepository repository;
    @Mock
    private Craftsperson mentee;

    private CraftspeopleService service;

    @Before
    public void setUp(){
        service = new CraftspeopleService(repository, null);
    }

    @Test(expected = InvalidLastMeetingDateException.class)
    public void cant_set_last_meeting_in_the_future() {
        service.setLastMeeting(1, (int) Instant.now().plus(2, ChronoUnit.DAYS).getEpochSecond());
    }

    @Test
    public void removing_a_mentor_should_remove_the_last_meeting() {
        given_a_craftsperson_and_his_mentor();
        when_the_mentor_is_removed();
        then_both_the_mentor_and_the_last_meeting_are_removed();
    }

    private void then_both_the_mentor_and_the_last_meeting_are_removed() {
        then(mentee).should().setMentorId(null);
        then(mentee).should().setLastMeeting(null);
        then(repository).should().save(mentee);
    }

    private void when_the_mentor_is_removed() {
        service.removeMentor(ID);
    }

    private void given_a_craftsperson_and_his_mentor() {
        given(repository.findById(ID)).willReturn(Optional.of(mentee));
    }
}
