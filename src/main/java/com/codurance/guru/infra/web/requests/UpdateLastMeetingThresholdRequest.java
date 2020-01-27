package com.codurance.guru.infra.web.requests;

import javax.validation.constraints.NotNull;

public class UpdateLastMeetingThresholdRequest {

    @NotNull(message = "lastMeetingThresholdsInWeeks must have a value")
    private Integer lastMeetingThresholdsInWeeks;

    public Integer getLastMeetingThresholdsInWeeks() {
        return lastMeetingThresholdsInWeeks;
    }

    public void setLastMeetingThresholdsInWeeks(Integer lastMeetingThresholdsInWeeks) {
        this.lastMeetingThresholdsInWeeks = lastMeetingThresholdsInWeeks;
    }
}
