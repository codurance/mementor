package com.codurance.guru.infra.repository;

import com.codurance.guru.core.configuration.lastmeeting.threshold.LastMeetingThreshold;
import com.codurance.guru.core.configuration.lastmeeting.threshold.LastMeetingThresholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class LastMeetingThresholdRepositoryImpl implements LastMeetingThresholdRepository {

    @Autowired
    LastMeetingThresholdJpaRepository jpaRepository;

    @Override
    public void updateThreshold(int newThresholdValue) {
        LastMeetingThreshold currentConfig = jpaRepository.findTopByOrderByIdDesc();
        currentConfig.setLastMeetingThresholdsInWeeks(newThresholdValue);
        jpaRepository.save(currentConfig);
    }

    public LastMeetingThreshold getConfig() {
        return jpaRepository.findTopByOrderByIdDesc();
    }

    public long count() {
        return jpaRepository.count();
    }

    public void save(LastMeetingThreshold lastMeetingThreshold) {
        jpaRepository.save(lastMeetingThreshold);
    }

    public void deleteAll() {
        jpaRepository.deleteAll();
    }
}

@Repository
interface LastMeetingThresholdJpaRepository extends JpaRepository<LastMeetingThreshold, Long> {
    LastMeetingThreshold findTopByOrderByIdDesc();
}
