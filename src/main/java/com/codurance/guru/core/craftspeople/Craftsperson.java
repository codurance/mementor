package com.codurance.guru.core.craftspeople;

import com.codurance.guru.infra.web.serialization.CraftspersonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "craftspeople")
@JsonSerialize(using = CraftspersonSerializer.class)
public class Craftsperson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne
    private Craftsperson mentor;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mentor")
    private List<Craftsperson> mentees;
    @Column(name = "last_meeting")
    private Instant lastMeeting;

    public Craftsperson() { }

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

    public List<Integer> getMentees() {
        return mentees.stream()
                .map(Craftsperson::getId)
                .collect(Collectors.toList());
    }

    public void setMentees(List<Craftsperson> mentees) {
        this.mentees = mentees;
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
        return Optional.of(mentor.getId());
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
