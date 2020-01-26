package com.codurance.guru.core.configuration.lastmeeting.threshold;

public class LastMeetingThreshold {

    private Integer lastMeetingThresholdsInWeeks;

    public LastMeetingThreshold() {}

    public LastMeetingThreshold(int lastMeetingThresholdsInWeeks) {
        this.lastMeetingThresholdsInWeeks = lastMeetingThresholdsInWeeks;
    }

    public Integer getLastMeetingThresholdsInWeeks() {
        return lastMeetingThresholdsInWeeks;
    }

    public void setLastMeetingThresholdsInWeeks(Integer lastMeetingThresholdsInWeeks) {
        this.lastMeetingThresholdsInWeeks = lastMeetingThresholdsInWeeks;
    }
}
