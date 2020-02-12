package com.codurance.guru.audits;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "events")
@JsonSerialize
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String createdBy;
    private String message;
    private Instant created;

    public Event() {
    }

    public Event(String createdBy, String message) {
        this.createdBy = createdBy;
        this.message = message;
        this.created = Instant.now();
    }

    public Integer getId() {
        return id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreated() {
        return created;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
