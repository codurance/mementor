package com.codurance.guru.craftspeople;

import com.codurance.guru.craftspeople.exceptions.InvalidLastMeetingDateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RunWith(MockitoJUnitRunner.class)
public class CraftspeopleServiceUnitTest {

    @Mock
    private CraftspeopleRepository repository;

    private CraftspeopleService service;
    private CraftspeopleValidator validator;

    @Before
    public void setUp(){
        service = new CraftspeopleService(repository, validator);
    }

    @Test(expected = InvalidLastMeetingDateException.class)
    public void cant_set_last_meeting_in_the_future() {
        service.setLastMeeting(1, (int) Instant.now().plus(2, ChronoUnit.DAYS).getEpochSecond());
    }
}
