package com.codurance.guru.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CraftspeopleService {

    @Autowired
    private CraftspeopleRepository repository;

    public Craftsperson retrieveCraftsperson(Integer craftspersonId) {
        return repository.findById(craftspersonId).orElse(new Craftsperson());
    }

    public void addDummyData() {
        Craftsperson mentor1 = repository.save(new Craftsperson("Arnaud", "CLAUDEL"));
        Craftsperson mentor2 = repository.save(new Craftsperson("Stephane", "Meny"));
        Craftsperson mentor3 = repository.save(new Craftsperson("Sandro", "Mancuso"));
        repository.save(new Craftsperson("Giulio", "Perrone"));
        repository.save(new Craftsperson("Jose", "Wenzel", mentor1));
        repository.save(new Craftsperson("Etienne", "Mustow", mentor2));
        repository.save(new Craftsperson("Ed", "Rixon", mentor3));
        repository.save(new Craftsperson("Michael", "Jackson"));
        repository.save(new Craftsperson("Mahatma", "Gandhi"));
    }

    public List<Craftsperson> retrieveAllCraftsperson() {
        return repository.findAll();
    }
}
