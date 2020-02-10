package com.codurance.guru.audits;

import com.codurance.guru.craftspeople.Craftsperson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @Mock
    EventRepository repository;

    @Test
    public void can_save_an_event() {
        EventService eventService = new EventService(repository);

        Event eventToSave = new Event(new Craftsperson("John", "Doe0"),"Message 0");

        eventService.addEvent(eventToSave);

        verify(repository, times(1)).save(eventToSave);

    }

    @Test
    public void retrieve_all_the_events() {
        EventService eventService = new EventService(repository);

        List<Event> events = new ArrayList<>();

        events.add(new Event(new Craftsperson("John", "Doe0"),"Message 0"));
        events.add(new Event(new Craftsperson("John", "Doe1"),"Message 1"));
        events.add(new Event(new Craftsperson("John", "Doe2"),"Message 2"));
        events.add(new Event(new Craftsperson("John", "Doe3"),"Message 3"));

        when(repository.findAll()).thenReturn(events);

        assertEquals(events, eventService.retrieveAll());

    }
}
