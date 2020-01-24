package com.codurance.guru.core.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicConfigurationRepository extends JpaRepository<DynamicConfiguration, Long> {

    DynamicConfiguration findTopByOrderByIdDesc();
}
