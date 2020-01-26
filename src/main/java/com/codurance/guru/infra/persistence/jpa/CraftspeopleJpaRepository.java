package com.codurance.guru.infra.persistence.jpa;

import com.codurance.guru.infra.persistence.entity.CraftspersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CraftspeopleJpaRepository extends JpaRepository<CraftspersonEntity, Integer> {
    List<CraftspersonEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
