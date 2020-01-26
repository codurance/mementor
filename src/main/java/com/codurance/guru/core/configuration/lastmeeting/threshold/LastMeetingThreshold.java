package com.codurance.guru.core.configuration.lastmeeting.threshold;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LastMeetingThreshold {

    @Id
    @GeneratedValue
    private Long id;

    @Column
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
