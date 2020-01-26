package com.codurance.guru.core.configuration.lastmeeting.threshold;

import com.codurance.guru.core.configuration.lastmeeting.threshold.exceptions.LastMeetingThresholdNotGreaterThanZeroException;

public class LastMeetingThresholdService {
    private LastMeetingThresholdRepository repository;

    public LastMeetingThresholdService(LastMeetingThresholdRepository repository) {
        this.repository = repository;
    }

    public void updateThreshold(int newThresholdValue) {
        if(newThresholdValue <= 0) {
            throw new LastMeetingThresholdNotGreaterThanZeroException();
        }
        repository.updateThreshold(newThresholdValue);
    }

    public LastMeetingThreshold getCurrentThreshold() {
        return repository.getCurrentThreshold();
    }
}
