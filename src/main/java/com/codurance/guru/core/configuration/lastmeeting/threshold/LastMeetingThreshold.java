package com.codurance.guru.core.configuration.lastmeeting.threshold;

public class LastMeetingThreshold {

    private final int lastMeetingThresholdsInWeeks;

    public LastMeetingThreshold(int lastMeetingThresholdsInWeeks) {
        this.lastMeetingThresholdsInWeeks = lastMeetingThresholdsInWeeks;
    }

    public Integer getLastMeetingThresholdsInWeeks() {
        return lastMeetingThresholdsInWeeks;
    }
}