package com.codurance.guru.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CraftspeopleService {

    @Autowired
    private CraftspeopleRepository repository;

    public Optional<Craftsperson> retrieveCraftsperson(Integer craftspersonId) {
        return repository.findById(craftspersonId);
    }

    public List<Craftsperson> retrieveAllCraftsperson() {
        return repository.findAll();
    }

    public void addMentor(int mentorId, int menteeId) {
        Craftsperson mentor = repository.findById(mentorId).get();
        Craftsperson mentee = repository.findById(menteeId).get();

        mentee.setMentor(mentor);
        repository.save(mentee);
    }

    public void deleteCraftsperson(Integer craftspersonId) {
        repository.deleteById(craftspersonId);
    }
}
