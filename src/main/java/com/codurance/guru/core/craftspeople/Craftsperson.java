package com.codurance.guru.core.craftspeople;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Craftsperson {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer mentorId;
    private List<Integer> menteeIds = new ArrayList<>();
    private Instant lastMeeting;

    public Craftsperson(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Craftsperson(Integer id, String firstName, String lastName, Integer mentorId) {
        this(firstName, lastName);
        this.id = id;
        this.mentorId = mentorId;
    }

    public Craftsperson(String firstName, String lastName, Integer mentorId) {
        this(firstName, lastName);
        this.mentorId = mentorId;
    }

    public Craftsperson(String firstName, String lastName, List<Integer> menteeIds) {
        this(firstName, lastName);
        this.menteeIds = menteeIds;
    }

    public Craftsperson(String firstName, String lastName, Integer mentorId, Instant lastMeeting) {
        this(firstName, lastName, mentorId);
        this.lastMeeting = lastMeeting;
    }

    public Craftsperson(Integer id, String firstName, String lastName, Integer mentorId, List<Integer> menteeIds, Instant lastMeeting) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mentorId = mentorId;
        this.menteeIds = menteeIds;
        this.lastMeeting = lastMeeting;
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
