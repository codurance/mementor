package com.codurance.guru.model.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CraftspeopleService {

    @Autowired
    private CraftspeopleRepository repository;

    public Craftsperson retrieveCraftsperson(Integer craftspersonId) {
        return repository.findById(craftspersonId).orElse(new Craftsperson());
    }
}
