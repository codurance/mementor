package com.codurance.guru.infra.persistence.entity;

import com.codurance.guru.core.craftspeople.Craftsperson;
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
public class CraftspersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne
    private CraftspersonEntity mentor;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mentor")
    private List<CraftspersonEntity> mentees;
    @Column(name = "last_meeting")
    private Instant lastMeeting;

    public CraftspersonEntity() {

    }

    public CraftspersonEntity(Integer id) {
        this.id = id;
    }

    public CraftspersonEntity(Craftsperson craftsperson) {
        this.id = craftsperson.getId();
        this.firstName = craftsperson.getFirstName();
        this.lastName = craftsperson.getLastName();
        this.lastMeeting = craftsperson.getLastMeeting()
                .orElse(null);

        this.mentor = craftsperson.getMentorId()
            .map(CraftspersonEntity::new)
            .orElse(null);

        this.mentees = craftsperson.getMenteeIds().stream()
                .map(CraftspersonEntity::new)
                .collect(Collectors.toList());
    }

    public Craftsperson toPOJO() {
        Integer mentorId = mentor == null ? null : mentor.id;
        List<Integer> menteesId = mentees.stream()
            .map(CraftspersonEntity::getId)
            .collect(Collectors.toList());

        return new Craftsperson(
                id, firstName, lastName, mentorId, menteesId, lastMeeting
        );
    }

    public List<CraftspersonEntity> getMentees() {
        return mentees;
    }

    public void setMentees(List<CraftspersonEntity> mentees) {
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

    public void setMentor(CraftspersonEntity mentor) {
        this.mentor = mentor;
    }


    public Optional<Instant> getLastMeeting() {
        return Optional.ofNullable(lastMeeting);
    }

    public void setLastMeeting(Instant lastMeeting){
        this.lastMeeting = lastMeeting;
    }
}
