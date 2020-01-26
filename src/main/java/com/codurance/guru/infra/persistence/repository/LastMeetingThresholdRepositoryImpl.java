package com.codurance.guru.infra.persistence.repository;

import com.codurance.guru.core.configuration.lastmeeting.threshold.LastMeetingThreshold;
import com.codurance.guru.core.configuration.lastmeeting.threshold.LastMeetingThresholdRepository;
import com.codurance.guru.infra.persistence.entity.LastMeetingThresholdEntity;
import com.codurance.guru.infra.persistence.jpa.LastMeetingThresholdJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LastMeetingThresholdRepositoryImpl implements LastMeetingThresholdRepository {

    @Autowired
    LastMeetingThresholdJpaRepository jpaRepository;

    @Override
    public void updateThreshold(int newThresholdValue) {
        LastMeetingThresholdEntity currentConfig = jpaRepository.findTopByOrderByIdDesc();
        currentConfig.setLastMeetingThresholdsInWeeks(newThresholdValue);
        jpaRepository.save(currentConfig);
    }

    @Override
    public LastMeetingThreshold getCurrentThreshold() {
        return jpaRepository.findTopByOrderByIdDesc().toPOJO();
    }

    public LastMeetingThreshold getConfig() {
        return jpaRepository.findTopByOrderByIdDesc().toPOJO();
    }

    public long count() {
        return jpaRepository.count();
    }

    public void save(LastMeetingThreshold lastMeetingThreshold) {
        jpaRepository.save(new LastMeetingThresholdEntity(lastMeetingThreshold));
    }

    public void deleteAll() {
        jpaRepository.deleteAll();
    }
}

