package com.codurance.guru.core.craftspeople.requests;

import javax.validation.constraints.NotNull;

public class RemoveMentorRequest {

    @NotNull(message = "Mentee Id must have a value")
    private int menteeId;

    public int getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(int menteeId) {
        this.menteeId = menteeId;
    }
}
