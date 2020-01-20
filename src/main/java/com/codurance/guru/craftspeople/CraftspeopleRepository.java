package com.codurance.guru.craftspeople;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CraftspeopleRepository extends JpaRepository<Craftsperson, Integer> {

    @Query("SELECT u FROM Craftsperson u WHERE u.firstName = ?1 and u.lastName = ?2")
    List<Craftsperson> findAllByFirstAndLastName(String firstName, String lastName);

}
