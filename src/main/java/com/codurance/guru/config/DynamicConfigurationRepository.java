package com.codurance.guru.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface DynamicConfigurationRepository extends JpaRepository<DynamicConfiguration, Long> {

    DynamicConfiguration findTopByOrderByIdDesc();
}
