package com.codurance.guru.audits;

import com.codurance.guru.craftspeople.Craftsperson;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String createdBy;
    String message;
    Instant created;

    public Event() { }

    public Event(Craftsperson createdBy, String message) {
        this.createdBy = createdBy.getFullName();
        this.message = message;
        this.created = Instant.now();
    }

    public String getCreatedBy() {
        return createdBy;
    }
}
