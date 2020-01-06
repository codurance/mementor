package com.codurance.guru.model.craftspeople;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CraftspeopleRepository extends JpaRepository<Craftsperson, Integer> {

}
