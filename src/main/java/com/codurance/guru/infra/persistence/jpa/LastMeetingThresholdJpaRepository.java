package com.codurance.guru.infra.persistence.jpa;

import com.codurance.guru.infra.persistence.entity.LastMeetingThresholdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastMeetingThresholdJpaRepository extends JpaRepository<LastMeetingThresholdEntity, Long> {
    LastMeetingThresholdEntity findTopByOrderByIdDesc();
}
