package com.codurance.guru.model.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CraftspeopleService {

    @Autowired
    private CraftpeopleRepository repository;

    public Craftsperson retrieveStudent(Integer craftspersonId) {
        return repository.findById(craftspersonId).orElse(new Craftsperson());
    }
}
