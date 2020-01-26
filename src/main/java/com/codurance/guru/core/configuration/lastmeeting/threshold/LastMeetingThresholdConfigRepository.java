package com.codurance.guru.core.configuration.lastmeeting.threshold;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastMeetingThresholdConfigRepository extends JpaRepository<LastMeetingThreshold, Long> {

    LastMeetingThreshold findTopByOrderByIdDesc();
}
