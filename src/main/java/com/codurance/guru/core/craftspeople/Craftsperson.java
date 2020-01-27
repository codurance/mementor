package com.codurance.guru.core.craftspeople;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Craftsperson {

    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final Integer mentorId;
    private final List<Integer> menteeIds;
    private final Instant lastMeeting;

    public Craftsperson(Integer id, String firstName, String lastName, Integer mentorId, Instant lastMeeting, List<Integer> menteeIds) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mentorId = mentorId;
        this.lastMeeting = lastMeeting;
        this.menteeIds = menteeIds;
    }

    public Craftsperson(Integer id, String firstName, String lastName, Integer mentorId, Instant lastMeeting) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mentorId = mentorId;
        this.lastMeeting = lastMeeting;
        this.menteeIds = new ArrayList<>();
    }

    public Craftsperson(String firstName, String lastName) {
        this(null, firstName, lastName, null, null);
    }

    public Craftsperson(Integer id, String firstName, String lastName, Integer mentorId) {
        this(id, firstName, lastName, mentorId, null);
    }

    public Craftsperson(String firstName, String lastName, Integer mentorId) {
        this(null, firstName, lastName, mentorId, null);
    }

    public Craftsperson(String firstName, String lastName, List<Integer> menteeIds) {
        this(null, firstName, lastName, null, null, menteeIds);
    }

    public Craftsperson(String firstName, String lastName, Integer mentorId, Instant lastMeeting) {
        this(null, firstName, lastName, mentorId, lastMeeting);
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Optional<Integer> getMentorId() {
        return Optional.ofNullable(mentorId);
    }

    public List<Integer> getMenteeIds() {
        return menteeIds;
    }

    public Optional<Instant> getLastMeeting() {
        return Optional.ofNullable(lastMeeting);
    }

}
