package com.codurance.guru.model.craftspeople;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CraftpeopleRepository extends CrudRepository<Craftsperson, Integer> {

}
