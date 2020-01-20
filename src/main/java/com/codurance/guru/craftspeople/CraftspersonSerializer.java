package com.codurance.guru.craftspeople;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class CraftspersonSerializer extends StdSerializer<Craftsperson> {

    protected CraftspersonSerializer() {
        super(Craftsperson.class);
    }

    @Override
    public void serialize(Craftsperson craftsperson, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Person mentor = craftsperson.getMentor()
                .map(Person::new)
                .orElse(null);

        List<Person> mentees = craftsperson.getMentees().stream()
                .map(Person::new)
                .collect(Collectors.toList());

        Long lastMeeting = craftsperson.getLastMeeting()
                .map(Instant::getEpochSecond)
                .orElse(null);

        gen.writeObject(new SerializableCraftsperson(craftsperson, mentor, mentees, lastMeeting));
    }
}

class Person {
    private Integer id;
    private String firstName;
    private String lastName;

    public Person(Craftsperson craftsperson) {
        this(craftsperson.getId(), craftsperson.getFirstName(), craftsperson.getLastName());
    }

    public Person(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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
}

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