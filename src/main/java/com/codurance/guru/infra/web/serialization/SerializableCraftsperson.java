package com.codurance.guru.infra.web.serialization;

import com.codurance.guru.core.craftspeople.Craftsperson;

import java.util.List;

class SerializableCraftsperson extends Person {
    private Person mentor;
    private List<Person> mentees;
    private Long lastMeeting;

    public SerializableCraftsperson(Craftsperson craftsperson, Person mentor, List<Person> mentees, Long lastMeeting) {
        super(craftsperson);
        this.mentor = mentor;
        this.mentees = mentees;
        this.lastMeeting = lastMeeting;
    }

    public Person getMentor() {
        return mentor;
    }

    public void setMentor(Person mentor) {
        this.mentor = mentor;
    }

    public List<Person> getMentees() {
        return mentees;
    }

    public void setMentees(List<Person> mentees) {
        this.mentees = mentees;
    }

    public Long getLastMeeting() {
        return lastMeeting;
    }

    public void setLastMeeting(Long lastMeeting) {
        this.lastMeeting = lastMeeting;
    }
}
