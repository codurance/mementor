package com.codurance.guru.infra.persistence.entity;

import com.codurance.guru.core.configuration.lastmeeting.threshold.LastMeetingThreshold;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LastMeetingThresholdEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer lastMeetingThresholdsInWeeks;

    public LastMeetingThresholdEntity() {}

    public LastMeetingThresholdEntity(int lastMeetingThresholdsInWeeks) {
        this.lastMeetingThresholdsInWeeks = lastMeetingThresholdsInWeeks;
    }

    public LastMeetingThresholdEntity(LastMeetingThreshold pojo) {
        this(pojo.getLastMeetingThresholdsInWeeks());
    }

    public LastMeetingThreshold toPOJO() {
        return new LastMeetingThreshold(lastMeetingThresholdsInWeeks);
    }

    public Integer getLastMeetingThresholdsInWeeks() {
        return lastMeetingThresholdsInWeeks;
    }

    public void setLastMeetingThresholdsInWeeks(Integer lastMeetingThresholdsInWeeks) {
        this.lastMeetingThresholdsInWeeks = lastMeetingThresholdsInWeeks;
    }
}
