package com.codurance.guru.audits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Auditor {

    @Autowired
    EventService eventService;

    public Auditor() {
    }

    public void addMentor(String user, String message) {
        eventService.addEvent(new Event(user, message));
    }

}
