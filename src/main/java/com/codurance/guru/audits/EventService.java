package com.codurance.guru.audits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventService {

    @Autowired
    EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public Event addEvent(Event event) {
        return repository.saveAndFlush(event);
    }

    public List<Event> retrieveAll() {
        return repository.findAll();
    }
}
