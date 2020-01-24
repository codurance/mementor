package com.codurance.guru.core.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DynamicConfiguration {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer lastMeetingThresholdsInWeeks;

    public DynamicConfiguration() {}

    public DynamicConfiguration(int lastMeetingThresholdsInWeeks) {
        this.lastMeetingThresholdsInWeeks = lastMeetingThresholdsInWeeks;
    }

    public Integer getLastMeetingThresholdsInWeeks() {
        return lastMeetingThresholdsInWeeks;
    }

    public void setLastMeetingThresholdsInWeeks(Integer lastMeetingThresholdsInWeeks) {
        this.lastMeetingThresholdsInWeeks = lastMeetingThresholdsInWeeks;
    }
}
