package com.codurance.guru.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CraftspeopleSampleData {

    @Autowired
    private CraftspeopleRepository repository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        Craftsperson sandro = repository.save(new Craftsperson("Sandro", "Mancuso"));
        Craftsperson steve = repository.save(new Craftsperson("Steve", "Lydford"));
        Craftsperson stephane = repository.save(new Craftsperson("Stephane", "Meny"));
        Craftsperson arnaud = repository.save(new Craftsperson("Arnaud", "CLAUDEL", sandro));
        repository.save(new Craftsperson("Jose", "Wenzel", sandro));
        repository.save(new Craftsperson("Etienne", "Mustow", sandro));
        repository.save(new Craftsperson("Ed", "Rixon", steve));
        repository.save(new Craftsperson("Michael", "Jackson",steve));
        repository.save(new Craftsperson("Giulio", "Perrone", steve));
        repository.save(new Craftsperson("Mahatma", "Gandhi", arnaud));
        repository.save(new Craftsperson("Bob", "Martin", arnaud));
        repository.save(new Craftsperson("Lev", "Boris", stephane));
    }
}
