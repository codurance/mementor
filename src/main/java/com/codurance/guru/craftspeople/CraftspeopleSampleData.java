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

        Craftsperson mashB = repository.save(new Craftsperson("Mashooq", "Badar"));
        Craftsperson sandroM = repository.save(new Craftsperson("Sandro", "Mancuso"));
        Craftsperson steveL = repository.save(new Craftsperson("Steve", "Lydford"));
        Craftsperson chrisE = repository.save(new Craftsperson("Christopher", "Eyre", steveL));
        Craftsperson liamG = repository.save(new Craftsperson("Liam", "Griffin-Jowett", chrisE));
        Craftsperson chrisB = repository.save(new Craftsperson("Chris", "Bimson", sandroM));
        Craftsperson neilK = repository.save(new Craftsperson("Neil", "Kidd", chrisB));
        Craftsperson richW = repository.save(new Craftsperson("Richard", "Wild", steveL));
        Craftsperson jorgeG = repository.save(new Craftsperson("Jorge", "Gueorguiev", mashB));
        Craftsperson alfredoF = repository.save(new Craftsperson("Alfredo", "Fernández", sandroM));
        Craftsperson francescoG = repository.save(new Craftsperson("Francesco", "Gigli", chrisB));
        Craftsperson danB = repository.save(new Craftsperson("Dan", "Bunea", mashB));
        Craftsperson jordiC = repository.save(new Craftsperson("Jordi", "Creus", danB));
        repository.save(new Craftsperson("Bartomeu", "Galmés", jordiC));
        repository.save(new Craftsperson("Scott", "Edwards", steveL));
        repository.save(new Craftsperson("Rory", "Collins", chrisE));
        repository.save(new Craftsperson("Chris", "Reinmuller", alfredoF));
        repository.save(new Craftsperson("Pierre-Jean", "Baraud", francescoG));
        repository.save(new Craftsperson("Luciano", "Palma", sandroM));
        repository.save(new Craftsperson("Mattsi", "Jansky", neilK));
        repository.save(new Craftsperson("Alasdair", "Smith", liamG));
        repository.save(new Craftsperson("Andrew", "Pattison", mashB));
        repository.save(new Craftsperson("Sam", "Davies", richW));
        repository.save(new Craftsperson("Tony", "Georgiadis", richW));
        repository.save(new Craftsperson("Sandro", "Viljoen", liamG));
        repository.save(new Craftsperson("Christina", "Ripley", jorgeG));
        repository.save(new Craftsperson("Kryszotof", "Napierala", jorgeG));

    }
}
