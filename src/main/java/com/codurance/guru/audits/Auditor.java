package com.codurance.guru.audits;

import com.codurance.guru.craftspeople.CraftspeopleService;
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
    CraftspeopleService craftspeopleService;

    public Auditor(EventService eventService, CraftspeopleService craftspeopleService) {
        this.eventService = eventService;
        this.craftspeopleService = craftspeopleService;
    }

    public Event addMentor(String actorName, int mentorId, int menteeId) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s added %s as a mentor to %s",
                        actorName,
                        getFullName(mentorId),
                        getFullName(menteeId))));
    }

    public Event addCraftsperson(String actorName, String firstName, String lastName) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s added %s %s as a new craftsperson",
                        actorName,
                        firstName,
                        lastName)));
    }

    public Event deleteCraftsperson(String actorName, String fullName) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s deleted %s from the system",
                        actorName,
                        fullName)));
    }

    public Event setLastMeeting(String actorName, int craftspersonId, Integer lastMeetingSeconds) {
        Instant lastMeetingInstant = Instant.ofEpochSecond(lastMeetingSeconds);

        return eventService.addEvent(new Event(actorName,
                String.format("%s updated the last meeting for %s to %s",
                        actorName,
                        getFullName(craftspersonId),
                        getDateAsString(lastMeetingInstant))));
    }

    public Event removeLastMeeting(String actorName, int craftspersonId) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s removed the last meeting for %s",
                        actorName,
                        getFullName(craftspersonId))));
    }

    public Event setMentee(String actorName, int menteeId, int mentorId) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s added %s as a mentee to %s",
                        actorName,
                        getFullName(menteeId),
                        getFullName(mentorId))));
    }

    public Event removeMentee(String actorName, int mentorId, int menteeId) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s removed %s as a mentor of %s",
                        actorName,
                        getFullName(mentorId),
                        getFullName(menteeId))));
    }

    public Event removeMentor(String actorName, int mentorId, int menteeId) {
        return eventService.addEvent(new Event(actorName,
                String.format("%s removed %s as a mentor of %s",
                        actorName,
                        getFullName(mentorId),
                        getFullName(menteeId))));
    }

    private String getFullName(int craftspersonId) {
        return craftspeopleService.retrieveCraftsperson(craftspersonId).get().getFullName();
    }

    private String getDateAsString(Instant lastMeetingInstant) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(Locale.UK).withZone(ZoneId.systemDefault()).format(lastMeetingInstant);
    }
}
