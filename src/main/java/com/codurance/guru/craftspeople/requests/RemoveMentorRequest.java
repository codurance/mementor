package com.codurance.guru.craftspeople.requests;

import javax.validation.constraints.NotBlank;

public class RemoveMentorRequest {

    @NotBlank(message = "Mentee Id must have a value")
    private int menteeId;

    public int getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(int menteeId) {
        this.menteeId = menteeId;
    }
}
