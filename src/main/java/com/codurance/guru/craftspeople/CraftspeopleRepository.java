package com.codurance.guru.craftspeople;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CraftspeopleRepository extends JpaRepository<Craftsperson, Integer> {

    List<Craftsperson> findByFirstNameAndLastName(String firstName, String lastName);

}
