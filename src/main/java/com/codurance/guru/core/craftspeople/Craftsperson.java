package com.codurance.guru.core.craftspeople;

import com.codurance.guru.infra.web.serialization.CraftspersonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonSerialize(using = CraftspersonSerializer.class)
public class Craftsperson {

    private Integer id;
    private String firstName;
    private String lastName;
    private Craftsperson mentor;
    private List<Craftsperson> mentees = new ArrayList<>();
    private Instant lastMeeting;

    public Craftsperson() { }

    public Craftsperson(Integer id) {
        this.id = id;
    }

    public Craftsperson(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Craftsperson(String firstName, String lastName, Craftsperson mentor, Instant lastMeeting) {
        this(firstName, lastName, mentor);
        this.lastMeeting = lastMeeting;
    }

    public Craftsperson(String firstName, String lastName, Craftsperson mentor) {
        this(firstName, lastName);
        this.mentor = mentor;
    }

    public Craftsperson(Integer id, String firstName, String lastName, Integer mentorId, List<Integer> menteesId, Instant lastMeeting) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mentor = new Craftsperson(mentorId);
        this.mentees = menteesId.stream()
            .map(Craftsperson::new)
            .collect(Collectors.toList());
        this.lastMeeting = lastMeeting;
    }

    public List<Integer> getMentees() {
        return mentees.stream()
                .map(Craftsperson::getId)
                .collect(Collectors.toList());
    }

    public void setMentees(List<Integer> mentees) {
        this.mentees = mentees.stream()
            .map(Craftsperson::new)
            .collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Optional<Integer> getMentor() {
        if(mentor == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(mentor.getId());
    }

    public void setMentor(Craftsperson mentor) {
        this.mentor = mentor;
    }


    public Optional<Instant> getLastMeeting() {
        return Optional.ofNullable(lastMeeting);
    }

    public void setLastMeeting(Instant lastMeeting){
        this.lastMeeting = lastMeeting;
    }
}
