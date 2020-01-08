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
}
