package com.codurance.guru.infra.repository;

import com.codurance.guru.core.craftspeople.Craftsperson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CraftspeopleJpaRepository extends JpaRepository<Craftsperson, Integer> {

    List<Craftsperson> findByFirstNameAndLastName(String firstName, String lastName);

}
