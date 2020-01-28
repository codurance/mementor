package com.codurance.guru.craftspeople;

import com.codurance.guru.craftspeople.exceptions.CraftspersonDoesntExistException;
import com.codurance.guru.craftspeople.exceptions.InvalidLastMeetingDateException;
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

    @Test(expected = CraftspersonDoesntExistException.class)
    public void cant_remove_last_meeting_from_an_unknown_craftsperson() throws CraftspersonDoesntExistException {
        service.removeLastMeeting(100);
    }
}
