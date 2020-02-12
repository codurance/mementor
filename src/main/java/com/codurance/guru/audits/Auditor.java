package com.codurance.guru.audits;

import com.codurance.guru.craftspeople.CraftspeopleRepository;
import com.codurance.guru.craftspeople.requests.AddCraftspersonRequest;
import com.codurance.guru.craftspeople.requests.AddMentorRequest;
import com.codurance.guru.craftspeople.requests.RemoveMentorRequest;
import com.codurance.guru.craftspeople.requests.UpdateLastMeetingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class Auditor {

    @Autowired
    EventService eventService;

    @Autowired
    CraftspeopleRepository craftspeopleRepository;

    public Auditor(EventService eventService, CraftspeopleRepository craftspeopleRepository) {
        this.eventService = eventService;
        this.craftspeopleRepository = craftspeopleRepository;
    }

    public Event addMentor(String actorName, AddMentorRequest request) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s added %s as a mentor to %s",
                        actorName,
                        getFullName(request.getMentorId()),
                        getFullName(request.getMenteeId()))));
    }

    public Event addCraftsperson(String actorName, AddCraftspersonRequest request) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s added %s %s as a new craftsperson",
                        actorName,
                        request.getFirstName(),
                        request.getLastName())));
    }

    public Event deleteCraftsperson(String actorName, String craftspersonBeingDeletedName) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s deleted %s from the system",
                        actorName,
                        craftspersonBeingDeletedName)));
    }

    public Event setLastMeeting(String actorName, UpdateLastMeetingRequest request) {
        Instant lastMeetingInstant = Instant.ofEpochSecond(request.getLastMeeting());

        return eventService.addEvent(new Event(actorName,
                String.format("%s updated the last meeting for %s to %s",
                        actorName,
                        getFullName(request.getCraftspersonId()),
                        getDateAsString(lastMeetingInstant))));
    }

    public Event removeLastMeeting(String actorName, int craftspersonId) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s removed the last meeting for %s",
                        actorName,
                        getFullName(craftspersonId))));
    }

    public Event setMentee(String actorName, AddMentorRequest request) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s added %s as a mentee to %s",
                        actorName,
                        getFullName(request.getMenteeId()),
                        getFullName(request.getMentorId()))));
    }

    public Event removeMentee(String actorName, RemoveMentorRequest request) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s removed %s as a mentor of %s",
                        actorName,
                        getFullName(request.getMentorId()),
                        getFullName(request.getMenteeId()))));
    }

    public Event removeMentor(String actorName, RemoveMentorRequest request) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s removed %s as a mentor of %s",
                        actorName,
                        getFullName(request.getMentorId()),
                        getFullName(request.getMenteeId()))));
    }

    private String getFullName(int craftspersonId) {
        return craftspeopleRepository.findById(craftspersonId).get().getFullName();
    }

    private String getDateAsString(Instant lastMeetingInstant) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(Locale.UK).withZone(ZoneId.systemDefault()).format(lastMeetingInstant);
    }
}
