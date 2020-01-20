package com.codurance.guru.craftspeople.requests;

import javax.validation.constraints.NotNull;

public class UpdateLastMeetingRequest {

    @NotNull(message = "Craftsperson Id must have a value")
    private int craftspersonId;
    @NotNull(message = "Last Meeting must have a value")
    private int lastMeeting;

    public int getCraftspersonId() {
        return craftspersonId;
    }

    public void setCraftspersonId(int craftspersonId) {
        this.craftspersonId = craftspersonId;
    }

    public int getLastMeeting() {
        return lastMeeting;
    }

    public void setLastMeeting(int lastMeeting) {
        this.lastMeeting = lastMeeting;
    }
}
